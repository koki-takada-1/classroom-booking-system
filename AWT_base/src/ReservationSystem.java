package client_system2;

public class ReservationSystem {
	public static void main(String argv[]) {
		ReservationControl	reservationControl	= new ReservationControl();				//ReservationControlクラスの呼び出し
		MainFrame			mainFrame			= new MainFrame(reservationControl);	//MainFrameクラスの呼び出し
		mainFrame.setBounds(5, 5, 900, 455);											//MainFrameのWindowを生成
		mainFrame.setVisible(true);														//MainFrameのWindowを表示
		
	}
}
