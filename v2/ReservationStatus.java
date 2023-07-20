package cbs;

import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ReservationStatus extends Dialog implements ActionListener, WindowListener, ItemListener{
	public ReservationStatus(Frame owner, ReservationControl rc) {
		super(owner, "教室予約状況", true);
		
	}
	
	@Override
    public void itemStateChanged(ItemEvent e) {
		// 選択された教室が変わったとき
		
	}	
	@Override
	public void windowOpened(WindowEvent e) {
		
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		setVisible( false);
		dispose();
		
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		
		
	}
	
	@Override
	public void windowIconified(WindowEvent e) {
		
		
	}
	
	@Override
	public void windowDeiconified(WindowEvent e) {
		
		
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		
		
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
}
