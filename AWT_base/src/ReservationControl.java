package client_system;

import java.awt.Dialog;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationControl {
	Connection		sqlCon;
	Statement		sqlStmt;
	String			sqlUserID = "puser";
	String			sqlPassword = "1234";
	//　予約システムのユーザIDおよびLogin状態
	String	   reservationUserID;
	private boolean flagLogin;

	//// ReservationControlクラスのコンストラクタ
	ReservationControl(){
		flagLogin = false;
	}

	//// MySQLに接続するためのメソッド
	private void connectDB() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			// MySQLに接続
			String url = "jdbc:mysql://localhost?useUnicode=true&characterEncoding=SJIS";
			sqlCon = DriverManager.getConnection(url, sqlUserID, sqlPassword);
			sqlStmt = sqlCon.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	//// MySQLから接続するためのメソッド
	private void closeDB() {
		try {
			sqlStmt.close();
			sqlCon.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	////ログイン・ログアウトボタンの処理
	public String loginLogout( MainFrame frame) {
		String res = "";
		if( flagLogin) {
			flagLogin = false;
			frame.buttonLog.setLabel(" ログイン ");
			frame.tfLoginID.setText(" 未ログイン ");
		} else {
			// ログインダイヤログ生成＋表示
			LoginDialog ld = new LoginDialog(frame);
			ld.setBounds( 100, 100, 350, 150);
			ld.setResizable( false);
			ld.setVisible(true);
			ld.setModalityType( Dialog.ModalityType.APPLICATION_MODAL);
			// IDとパスワードの入力がキャンセルされたら,Nullを結果として返し終了
			if ( ld.canceled) {
				return "";
			}

			// ユーザIDとパスワードが入力された場合の処理
			reservationUserID = ld.tfUserID.getText();
			String password = ld.tfPassword.getText();

			connectDB();
			try {
				// ユーザの情報を取得するクエリ
				String sql = "SELECT * FROM db_reservation.user WHERE user_id = '" + reservationUserID + "';";
				ResultSet rs = sqlStmt.executeQuery( sql);		// クエリを実行して結果セットを取得
				// パスワードチェック
				if( rs.next()) {
					String password_form_db = rs.getString("password");
					if( password_form_db.equals(password)) {
						flagLogin = true;
						frame.buttonLog.setLabel("ログアウト");
						frame.tfLoginID.setText(reservationUserID);
						res = "";
					} else {
						res = "IDまたはパスワードが違います";
					}
				} else {
					res = " IDが違います ";
				}
			} catch ( Exception e) {
				e.printStackTrace();
			}
			closeDB();
		}
		return res;
	}

	//// 教室概要ボタン押下時の処理を行うメソッド
	public String getFacilityExplanation(String facility_id) {
		String res = "";
		String exp = "";
		String openTime = "";
		String closeTime = "";
		connectDB();
		try {
			String sql = "SELECT * FROM db_reservation.facility WHERE facility_id = '" + facility_id + "';";
			ResultSet rs = sqlStmt.executeQuery(sql);
			if (rs.next()) {
				exp = rs.getString("explanation");
				openTime = rs.getString("open_time");
				closeTime = rs.getString("close_time");
				// 教室概要データの作成
				//res = "教室ID: " + facility_id + "\n";
				//res += "説明: " + exp + "\n";
				res = exp + "利用可能時間: " + openTime.substring(0, 5) + "~" + closeTime.substring(0, 5);
			} else {
				res = "教室番号が違います。";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		closeDB();
		return res;
	}

	////すべてのfacility_idを取得するメソッド
	public List getFacilityId() {
		List<String> facilityId = new ArrayList<String>();
		connectDB();
		try {
			String sql = "SELECT * FROM db_reservation.facility;";
			ResultSet rs = sqlStmt.executeQuery(sql);              //選択された教室IDと同じレコードを抽出
			while ( rs.next()) {                                   //レコードがなくなるまで繰り返す
				facilityId.add( rs.getString( "facility_id"));    //読みだしたデータのfacility_idをfacilityIdリストに追加
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return facilityId;
	}
	////新規予約ボタン押下時のメソッド
	public String makeReservation( MainFrame frame) {
		String res ="";										//	予約結果のメッセージを入れる
		if( flagLogin) {
			//新規予約画面生成
			ReservationDialog	rd = new ReservationDialog( frame,this);

			//新規予約画面を表示
			rd.setVisible( true);
			if( rd.canceled) {								//新規予約ダイアログでキャンセルボタンが押されたとき
				return res;									//	処理終了（表示メッセージ無し）
			}
			//新規予約画面から年月日を取得
			String ryear_str	= rd.tfYear.getText();		//新規予約ダイアログで設定された「年」を取得
			String rmonth_str	= rd.tfMonth.getText();		//新規予約ダイアログで設定された「月」を取得
			String rday_str		= rd.tfDay.getText();		///新規予約ダイアログで設定された「日」を取得
			//月と日が一桁だったら、前に0を付加
			if( rmonth_str.length() == 1) {
				rmonth_str = "0" + rmonth_str;
			}
			if( rday_str.length() == 1) {
				rday_str = "0" + rday_str;
			}
			String rdate = ryear_str + "-" + rmonth_str + "-" + rday_str;	//予約年月日をString型で合成

			//入力された日付が正しいのかのチェック
			try {
				DateFormat  df = new SimpleDateFormat("yyyy-MM-dd");
				df.setLenient(false);									//日付の厳密化チェックを有効化
				String	convData = df.format(df.parse(rdate));			//rdateを一度data型に変更し、String型に再変換
				if( !rdate.equals( convData)||( ryear_str.length() != 4)) {				//String→date→Stringで同じ文字に戻らないか、西暦が4桁でなければ書式エラー
					res = "日付の書式を修正してください　（年：西暦4桁、月:1~12、日：1~31（毎月末日まで））";
					return res;
				}	
			}catch (ParseException p) {					//rdateが日付として成立していない時の処理
				res = "日付の値を修正してください";
				return res;
				// TODO: handle exception
			}
			//新規予約画面から教室名、開始時刻、終了時刻を取得
			String	facility = rd.choiceFacility.getSelectedItem();		//選択された教室IDを取り出す
			String	st = rd.startHour.getSelectedItem() + ":" + rd.startMinute.getSelectedItem() + "00";		//選択された開始時間を取り出す
			String	et = rd.endHour.getSelectedItem() + ":" + rd.endMinute.getSelectedItem() + "00";			//っ選択された終了時間を取り出す

			if( st.compareTo( et) >= 0) {								//開始時刻と終了時刻が等しい（０）か終了時刻が早い（ー１）
				res = "開始時刻と終了時刻が同じか終了時刻の方が早くなっています";
			}else {
				try {
					//予約日時を取得
					Calendar justNow = Calendar.getInstance();			//現在時刻を取得
					SimpleDateFormat resDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					String now = resDate.format(justNow.getTime());		//現在時刻をMySQLのdate型文字列に変換

					connectDB();										//MySQLに接続
					//予約情報をMySQLに書き込む
					String sql = "INSERT INTO db_reservation.reservation( facility_id, user_id, date, day, start_time, end_time) VALUES( '"
							+ facility + "','" + reservationUserID + "','" + now + "','" + rdate + "','" + st + "'," + et + "');";
					sqlStmt.executeUpdate( sql);
					res = "予約されました";							//予約情報をDBに書き込む際にエラーが発生したとき
				}catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
				closeDB();										//MySQLの接続を切る
			}
		}else {
			res = "ログインしてください";
		}
		return res;
	}

	////指定教室の利用可能開始・終了時間を取得する
	public int[] getAvailableTime( String facility) {
		int[] abailableTime = { 0,0};									//開始時・終了時をこの配列に入れて呼び元に返す
		connectDB();
		try {
			String sql = "SELECT * FROM db_reservation.facility WHERE facility_id = " + facility + ";";
			ResultSet rs = sqlStmt.executeQuery(sql);					//選択された教室IDと同じレコードを抽出
			while( rs.next()) {											//レコードがなくなるまで繰り返す
				String timeData = rs.getString("open_time");			//該当レコードのopen_timeを取得
				timeData = timeData.substring( 0, 2);					//open_timeの「時」のみ取得
				abailableTime[0] = Integer.parseInt(timeData);			//open_timeの「時」を整数型に変換
				timeData = rs.getString("close_time");					//該当レコードのclose_timeを取得
				timeData = timeData.substring( 0, 2);					//close_timeの「時」のみ取得
				abailableTime[1] = Integer.parseInt(timeData);			//close_timeの「時」を整数型に変換
			}
		}catch (Exception e) {											//該当レコードがない、「時」を整数に変換できないなど
			e.printStackTrace();// TODO: handle exception
		}
		return abailableTime;											//open_time,close_timeの「時」を返す。エラーの場合、{0,0}が返る
	}
}







