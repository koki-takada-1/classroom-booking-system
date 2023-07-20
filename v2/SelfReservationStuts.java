package client_system;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;


public class SelfReservationStuts extends Dialog implements ActionListener, WindowListener, ItemListener {
	
	boolean canceled;
	ReservationControl rc;
	
	// パネル
	Panel panelNorth;
	Panel panelNorthSub1;
	Panel panelNorthSub2;
	Panel panelNorthSub3;
	Panel panelCenter;
	
	// 入力用コンポーネント
	TextField      tfYear, tfMonth, tfDay;
	
	// ボタン
	Button ButtonCancel;
	
	
	//テキストフィールドのインスタンス作成
	TextField  tfLoginID;				//ログインIDを表示するテキストフィールド
	TextField  tfCheckCount;			//チェック数をカウントして表示するテキストフィールド
	
	
	//テキストエリアエリアのインスタンス作成
	TextArea  StutsArea;			    //Button表示用テキストエリア
	
	
	// コンストラクタ
	public ReservationStuts(Frame owner, ReservationControl rc) {
		// スーパークラスであるDialogのコンストラクタを呼び出す
		super(owner, "自己予約確認", true);
		
		this.rc = rc;
		
		// 初期値キャンセルを設定
		canceled = true;
		
		//ログインID表示用のボックス生成
		tfLoginID = new TextField("ログインID:"+rc.reservationUserID, 20);
		tfLoginID.setEditable(false);

		// ボタンの生成
		ButtonCancel = new Button("キャンセル");
		// パネルの生成
		panelNorth   = new Panel();
		panelNorthSub1 = new Panel();
		panelNorthSub2 = new Panel();
		panelNorthSub3 = new Panel();
		panelCenter  = new Panel();

		//上部パネルに上下２つのパネルを追加
		panelNorth	= new Panel(new BorderLayout());
		panelNorth.add(panelNorthSub1,BorderLayout.NORTH);
		panelNorth.add(panelNorthSub2,BorderLayout.CENTER);
		panelNorth.add(panelNorthSub3,BorderLayout.SOUTH);
		panelNorth.setBackground(Color.WHITE);

		//メイン画面(MainFrame)に上パネルを追加
		add(panelNorth, BorderLayout.NORTH);

		//中央パネルにテキストメッセージ欄を設定
		panelCenter = new Panel();
		panelCenter.setBackground(Color.WHITE);
		StutsArea = new TextArea(20,105);
		StutsArea.setEditable(false);

		// 上部パネルに教室選択ボックス、年月日入力欄を配置
		//上部パネルの上パネルに「教室予約システム」というラベルと「ログイン」ボタンを追加
		panelNorthSub1.add(tfLoginID);
		panelNorthSub1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelNorthSub3.add(new Label("チェック数："));
		panelNorthSub3.add(tfCheckCount);
		panelNorthSub3.add(ButtonCancel);
		panelNorthSub3.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		//中央パネル
		panelCenter.add(StutsArea);
		

		//ReservationStutsをBorderLayoutに設定し、３つのパネルを追加
		setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);

		// Window Listenerを追加
		addWindowListener(this);
		// ボタンにアクションリスナを追加
		ButtonCancel.addActionListener(this);
		// 大きさの設定、Windowのサイズ変更不可の設定
		this.setBounds(5, 5, 900, 455);
		setResizable(false);		
	}


	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}


	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}


	@Override
	public void windowClosing(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
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
		if(e.getSource() == ButtonCancel) {							//ログインボタンが押されたとき
			result = ReservationControl.CancelReservation(this);			//loginLogoutメソッドを呼び出す
		}
		textMessage.setText(result);								//テキストエリアをControllerから戻り値を表示
	}
}
