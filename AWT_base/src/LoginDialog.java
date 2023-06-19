package client_system2;

import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends Dialog implements ActionListener, WindowListener{
	boolean		canceled;							//キャンセル:true	OK:false
	
	TextField	tfUserID;							//ユーザID入力用
	TextField	tfPassword;							//PW入力用テキストフィールド
	
	Button		buttonOK;							//OKボタンインスタンス
	Button		buttonCancel;						//キャンセルボタンインスタンス
	
	Panel		panelNorth;							//上部パネル
	Panel		panelCenter;						//中央パネル
	Panel		panelSouth;							//下部パネル
	
	//LOginDialogクラスコンストラクタ
	public LoginDialog(Frame arg0) {				//引数は、Dialog1所有者、タイトル、モーダル指定
		super(arg0, "Login", true);					//キャンセル状態トしておく
		canceled = true;
		
		//テキストフィールド生成
		tfUserID = new TextField("", 10);			//ユーザーID入力用TFインスタンス生成
		tfPassword = new TextField("", 10);			//PW入力用TFインスタンス生成
		tfPassword.setEchoChar('*');				//PWは*表示とする
		
		//ボタン生成
		buttonOK = new Button("OK");				//OKボタンインスタンス
		buttonCancel = new Button("キャンセル");		//キャンセル状態としておく
		
		//パネルの生成
		panelNorth = new Panel();					//上部パネルインスタンス生成
		panelNorth.add(new Label("ユーザID"));		
		panelNorth.add(tfUserID);
		panelCenter = new Panel();					//中央
		panelCenter.add(new Label("パスワード"));
		panelCenter.add(tfPassword);
		panelSouth = new Panel();					//下部
		panelSouth.add(buttonCancel);
		panelSouth.add(buttonOK);
		
		//LoginDialogをBorderLayoutに設定し、3つのパネル追加
		setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		
		//WindowListenerを追加
		addWindowListener(this);
		
		//ボタンにActionListenerを追加
		buttonOK.addActionListener(this);
		buttonCancel.addActionListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		setVisible(false);							//Window可視化
		canceled = true;							//ログインキャンセル
		dispose();									//リソース解放	
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
		if(e.getSource() == buttonCancel) {				//押下されたのがキャンセルボタンのとき
			canceled = true;							//ログインキャンセル
		}else if(e.getSource() == buttonOK) {			//押下されたのがOK
			canceled = false;							//認証処理
		}
		setVisible(false);								//LoginDialog不可視化
		dispose();										//LoginDialogde使っていたリソース解放
	}
}
