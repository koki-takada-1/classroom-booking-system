//Test コード編集テスト

package client_system;

import java.awt.*;
import java.awt.event.*;

public class LoginDialog extends Dialog implements ActionListener, WindowListener{
	boolean		canceled;
	
	TextField	tfUserID;
	TextField	tfPassword;
	
	Button		buttonOK;
	Button		buttonCancel;
	
	Panel		panelNorth;
	Panel		panelCenter;
	Panel		panelSouth;
	
	public LoginDialog(Frame arg0) {
		super(arg0, "Login", true);
		canceled = true;
		
		tfUserID = new TextField("", 10);
		tfPassword = new TextField("", 10);
		tfPassword.setEchoChar('*');
		
		buttonOK = new Button("OK");
		buttonCancel = new Button("キャンセル");
		
		panelNorth = new Panel();
		panelNorth.add(new Label("ユーザID"));
		panelNorth.add(tfUserID);
		panelCenter = new Panel();
		panelCenter.add(new Label("パスワード"));
		panelCenter.add(tfPassword);
		panelSouth = new Panel();
		panelSouth.add(buttonCancel);
		panelSouth.add(buttonOK);
		
		setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		
		addWindowListener(this);
		
		buttonOK.addActionListener(this);
		buttonCancel.addActionListener(this);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		setVisible(false);
		canceled = true;
		dispose();
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
		if(e.getSource() == buttonCancel) {
			canceled = true;
		}else if(e.getSource() == buttonOK) {
			canceled = false;
		}
		setVisible(false);
		dispose();
	}
}
