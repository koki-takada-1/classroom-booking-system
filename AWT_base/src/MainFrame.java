package client_system;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ArrayList;

public class MainFrame extends Frame implements ActionListener, WindowListener{
	ReservationControl	reservationControl;
	Panel				panelNorth;
	Panel				panelNorthSub1;
	Panel				panelNorthSub2;
	Panel				panelCenter;
	
	Button				buttonLog;
	Button				buttonExplanation;
	
	ChoiceFacility 		choiceFacility;
	
	TextField			tfLoginID;
	TextArea			textMessage;
	
	public MainFrame(ReservationControl rc) {
		reservationControl = rc;
		buttonLog = new Button("ログイン");
		buttonExplanation = new Button("教室概要");
		
		List<String> facilityId = new ArrayList<String>();
		facilityId = rc.getFacilityId();
		choiceFacility = new ChoiceFacility(facilityId);
		
		tfLoginID = new TextField("未ログイン", 12);
		tfLoginID.setEditable(false);
		
		setLayout(new BorderLayout());
		
		panelNorthSub1 = new Panel();
		panelNorthSub1.add(new Label("教室予約システム"));
		panelNorthSub1.add(buttonLog);
		panelNorthSub1.add(new Label("ログインID:"));
		panelNorthSub1.add(tfLoginID);
		
		panelNorthSub2 = new Panel();
		panelNorthSub2.add(new Label("教室"));
		panelNorthSub2.add(choiceFacility);
		panelNorthSub2.add(new Label(""))
		
		add(panelNorth, BorderLayout.NORTH);
		
		panelCenter = new Panel();
		textMessage = new TextArea(20, 80);
		textMessage.setEditable(false);
		panelCenter.add(textMessage);
		
		add(panelCenter, BorderLayout.CENTER);
		
		buttonLog.addActionListener(this);
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
		if(e.getSource() == buttonLog) {
			result = reservationControl.loginLogout(this);
		}
		textMessage.setText(result);
	}
}
