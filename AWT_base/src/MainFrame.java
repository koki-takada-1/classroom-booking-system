package client_system2;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends Frame implements ActionListener, WindowListener{
	ReservationControl	reservationControl;		//ReservationControlクラスのインスタンスを生成

	Panel  panelNorth;				//上部パネル
	Panel  panelNorthSub1;			//上部パネルの上
	Panel  panelNorthSub2;			//上部パネルの下
	Panel  panelCenter;			//中央パネル
	Panel  panelSouth;				//下部パネル

	//ボタンインスタンス作成
	Button  buttonLog;				//ログイン・ログアウトボタン
	Button	buttonExplanation;		//教室概要ボタン
	Button	buttonReservation;		//新規予約画面

	//コンボボックスのインスタンス作成
	ChoiceFacility 	choiceFacility;			//教室選択用コンボボックス			

	//テキストフィールドのインスタンス作成
	TextField  tfLoginID;				//ログインIDを表示するテキストフィールド

	//テキストエリアエリアのインスタンス作成
	TextArea  textMessage;			//結果表示用メッセージ欄

	//MainFrameコンストラクタ
	public MainFrame(ReservationControl rc) {
		reservationControl = rc;
		//ボタンの生成
		buttonLog = new Button("ログイン");
		buttonExplanation = new Button("教室概要");
		buttonReservation = new Button("新規予約");

		//教室選択用のコンボボックスの生成
		List<String> facilityId = new ArrayList<String>();
		facilityId = rc.getFacilityId();
		choiceFacility = new ChoiceFacility(facilityId);

		//ログインID表示用のボックス生成
		tfLoginID = new TextField("未ログイン", 12);
		tfLoginID.setEditable(false);

		//上と中央パネルを使うため、レイアウトマネージャー二BorderLayoutを設定
		setLayout(new BorderLayout());

		//上部パネルの上パネルに「教室予約システム」というラベルと「ログイン」ボタンを追加
		panelNorthSub1 = new Panel();
		panelNorthSub1.add(new Label("教室予約システム"));
		panelNorthSub1.add(buttonLog);
		panelNorthSub1.add(new Label("ログインID:"));
		panelNorthSub1.add(tfLoginID);

		//上部パネルの下パネルに教室選択及び教室概要ボタンを追加
		panelNorthSub2 = new Panel();
		panelNorthSub2.add(new Label("教室"));
		panelNorthSub2.add(choiceFacility);
		panelNorthSub2.add(new Label(""));
		panelNorthSub2.add(buttonExplanation);

		//上部パネルに上下２つのパネルを追加
		panelNorth	= new Panel(new BorderLayout());
		panelNorth.add(panelNorthSub1,BorderLayout.NORTH);
		panelNorth.add(panelNorthSub2,BorderLayout.CENTER);

		//メイン画面(MainFrame)に上パネルを追加
		add(panelNorth, BorderLayout.NORTH);

		//中央パネルにテキストメッセージ欄を設定
		panelCenter = new Panel();
		textMessage = new TextArea(20, 80);
		textMessage.setEditable(false);
		panelCenter.add(textMessage);

		//メイン画面(MainFrame)に中央パネルを追加
		add(panelCenter, BorderLayout.CENTER);

		//下部パネルに新規予約ボタンに追加
		panelSouth = new Panel();
		panelSouth.add(buttonReservation);
		//メイン画面(MainFrame)に下部パネルを追加
		add(panelSouth,BorderLayout.SOUTH);

		//ボタンのアクションリスナの追加
		buttonLog.addActionListener(this);
		buttonExplanation.addActionListener(this);
		buttonReservation.addActionListener(this);
		addWindowListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String result = new String();
		if(e.getSource() == buttonLog) {							//ログインボタンが押されたとき
			result = reservationControl.loginLogout(this);			//loginLogoutメソッドを呼び出す
		}else if(e.getSource() == buttonExplanation) {				//教室概要ボタンが押されたとき
			result = reservationControl.getFacilityExplanation(choiceFacility.getSelectedItem());//getFacilityExplanationを呼び出す
		}else if(e.getSource() == buttonReservation) {
			result = reservationControl.makeReservation(this);		//makeReservationを呼び出す
		}
		textMessage.setText(result);								//テキストエリアをControllerから戻り値を表示
	}
}
