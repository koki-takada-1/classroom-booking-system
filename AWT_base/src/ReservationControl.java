package client_system;

import java.sql.*;
import java.awt.*;

public class ReservationControl {
	Connection		sqlCon;
	Statement		sqlStmt;
	String			sqlUserID = "puser";
	String			sqlPassword = "1234";
	String			reservationUserID;
	private boolean flagLogin;
	
	ReservationControl(){
		flagLogin = false;
	}
	
	private void connectDB() {
		try {
			Class.forName("org.gjt.mm.mysql.Driver");
			String url = "jdbc:mysql://localhost?useUnicode=true&characterEncoding=SJIS";
			sqlCon = DriverManager.getConnection(url, sqlUserID, sqlPassword);
			sqlStmt = sqlCon.createStatement();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void closeDB() {
		try{
			sqlStmt.close();
			sqlCon.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public String loginLogout(MainFrame frame) {
		String res = "";
		if(flagLogin) {
			flagLogin = false;
			frame.buttonLog.setLabel("ログイン");
			frame.tfLoginID.setText("未ログイン");
		}else {
			LoginDialog ld = new LoginDialog(frame);
			ld.setBounds(100, 100, 350, 150);
			ld.setResizable(false);
			ld.setVisible(true);
			ld.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
			
			if(ld.canceled) {
				return "";
			}
			
			reservationUserID = ld.tfUserID.getText();
			String password = ld.tfPassword.getText();
			
			connectDB();
			
			try {
				String sql = "SELECT * FROM db_reservation.user WHERE user_id ='" + reservationUserID + "';";
				ResultSet rs = sqlStmt.executeQuery(sql);
				if(rs.next()) {
					String password_form_db = rs.getString("password");
					if(password_form_db.equals(password)) {
						flagLogin = true;
						frame.buttonLog.setLabel("ログアウト");
						frame.tfLoginID.setText(reservationUserID);
						res = "";
					}else {
						res = "IDまたはパスワードが違います。";
					}
				}else {
					res = "IDが違います。";
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			closeDB();
		}
		return res;
	}
}
