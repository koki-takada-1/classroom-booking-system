package client_system2;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

public class ReservationDialog extends Dialog implements ActionListener, WindowListener, ItemListener {
	
	boolean canceled;
	ReservationControl rc;
	
	// パネル
	Panel panelNorth;
	Panel panelCenter;
	Panel panelSouth;
	
	// 入力用コンポーネント
	ChoiceFacility choiceFacility;
	TextField      tfYear, tfMonth, tfDay;
	ChoiceHour     startHour;
	ChoiceMinute   startMinute;
	ChoiceHour     endHour;
	ChoiceMinute   endMinute;
	
	// ボタン
	Button buttonOK;
	Button buttonCancel;
	
	// コンストラクタ
	public ReservationDialog(Frame owner, ReservationControl rc) {
		// スーパークラスであるDialogのコンストラクタを呼び出す
		super(owner, "新規予約", true);
		
		this.rc = rc;
		
		// 初期値キャンセルを設定
		canceled = true;
		
		// 教室選択ボックスの作成
		List<String> facilityId = new ArrayList<String>();
		facilityId = rc.getFacilityId();
		choiceFacility = new ChoiceFacility(facilityId);
		// テキストフィールドの生成(年月日)
		tfYear       = new TextField("", 4);
		tfMonth      = new TextField("", 2);
		tfDay        = new TextField("", 2);
		// 開始時刻(時分)選択ボックスの生成
		startHour    = new ChoiceHour();
		startMinute  = new ChoiceMinute();
		// 終了時刻(時分)選択ボックスの生成
		endHour      = new ChoiceHour();
		endMinute    = new ChoiceMinute();
		
		// ボタンの生成
		buttonOK     = new Button("予約実行");
		buttonCancel = new Button("キャンセル");
		
		// パネルの生成
		panelNorth   = new Panel();
		panelCenter  = new Panel();
		panelSouth   = new Panel();
		
		// 上部パネルに教室選択ボックス、年月日入力欄を配置
		panelNorth.add(new Label("教室"));
		panelNorth.add(choiceFacility);
		panelNorth.add(new Label("予約日"));
		panelNorth.add(tfYear);
		panelNorth.add(new Label("年"));
		panelNorth.add(tfMonth);
		panelNorth.add(new Label("月"));
		panelNorth.add(tfDay);
		panelNorth.add(new Label("日"));
		
		// 中央パネルに予約開始時刻、終了時刻入力用選択ボックスを追加
		panelCenter.add(new Label("予約時間"));
		panelCenter.add(startHour);
		panelCenter.add(new Label("時"));
		panelCenter.add(startMinute);
		panelCenter.add(new Label("分〜"));
		panelCenter.add(endHour);
		panelCenter.add(new Label("時"));
		panelCenter.add(endMinute);
		panelCenter.add(new Label("分"));
		
		// 下部パネルに2つのボタンを追加
		panelSouth.add(buttonCancel);
		panelSouth.add(new Label("  "));
		panelSouth.add(buttonOK);
		
		// ReservationDialogをBorderLayoutに設定し、３つのパネルを追加
		setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		add(panelSouth, BorderLayout.SOUTH);
		
		// Window Listenerを追加
		addWindowListener(this);
		// ボタンにアクションリスナを追加
		buttonOK.addActionListener(this);
		buttonCancel.addActionListener(this);
		// 教室選択ボックス
		choiceFacility.addItemListener(this);
		startHour.addItemListener(this);
		endHour.addItemListener(this);
		
		// 選択された教室の、利用可能時刻の範囲を設定
		resetTimeRange(choiceFacility.getSelectedItem());
		
		// 大きさの設定、Windowのサイズ変更不可の設定
		this.setBounds(100, 100, 500, 150);
        setResizable(false);		
	}
////開始時間と終了時間を初期値に設定する
	private void resetTimeRange( String facility) {
		int[]	availableTime;
		// 指定教室の利用可能開始時刻・終了時刻を取得する.
		availableTime = rc.getAvailableTime( facility);
		// 時刻の範囲に初期値を設定する
		startHour.resetRange( availableTime[0],availableTime[1]);
		endHour.resetRange( availableTime[0],availableTime[1]);
	}
	
	// コンボボックスで選択している情報が変化したとき
	@Override
    public void itemStateChanged(ItemEvent e) {
		// 選択された教室が変わったとき
		if( e.getSource() == choiceFacility) {
			String	startTime = startHour.getSelectedItem();
			String	endTime   = endHour.getSelectedItem();
			resetTimeRange( choiceFacility.getSelectedItem());
			if( Integer.parseInt(startTime)< Integer.parseInt(startHour.getFirst())) {
				startTime = startHour.getFirst();
			}
			if( Integer.parseInt(endTime)> Integer.parseInt(endHour.getLast())) {
				endTime = endHour.getLast();
			}
			startHour.select( startTime);
			endHour.select( endTime);
		// 利用開始時刻(時)が変わったとき
		}else if(e.getSource()== startHour) {
			// 開始時刻が変更されたら,終了時刻入力欄の時を開始時刻に合わせる
		 int	start = Integer.parseInt( startHour.getSelectedItem());
		 String	endTime = endHour.getSelectedItem();
		 endHour.resetRange( start,Integer.parseInt(endHour.getLast()));
		 if( Integer.parseInt(endTime)>= start) {
			 endHour.select( endTime);
		 }
		// 利用終了時刻(時）が変わったとき
		}else if(e.getSource()== endHour) {
			// 開始時刻が変更されたら,開始時刻入力欄の時を終了時刻に合わせる
		 int	end = Integer.parseInt( endHour.getSelectedItem());
		 String	startTime = startHour.getSelectedItem();
		 startHour.resetRange(Integer.parseInt(startHour.getFirst()),end);
		 if( Integer.parseInt(startTime)<= end) {
			 startHour.select( startTime);
		 }
		}
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
		if( e.getSource()== buttonCancel) {
			setVisible( false);
			dispose();
		}else if(e.getSource()== buttonOK) {
			canceled = false;
			setVisible(false);
			dispose();
		}
	}
}
