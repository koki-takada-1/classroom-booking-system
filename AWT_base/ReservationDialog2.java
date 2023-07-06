package client_system;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ReservationDialog extends Dialog implements ActionListener, WindowListener, ItemListener {
	
	boolean canceled;
	ReservationControl rc;
	
	// パネル
	Panel panelNorth;
	Panel panelNorthSub1;
	Panel panelNorthSub2;
	Panel panelNorthSub3;
	Panel panelCenter;
	
	// 入力用コンポーネント
	ChoiceFacility choiceFacility;
	TextField      tfYear, tfMonth, tfDay;
	ChoiceHour     startHour;
	ChoiceMinute   startMinute;
	ChoiceHour     endHour;
	ChoiceMinute   endMinute;
	
	// ボタン
	Button buttonOK;
	Button prevButton;
    Button nextButton;
    Button LogoutButton;
	
	//テキストフィールドのインスタンス作成
	TextField  tfLoginID;				//ログインIDを表示するテキストフィールド
	TextField  tfDate;
	
	
	//テキストエリアエリアのインスタンス作成
	TextArea  ButtonArea;			    //Button表示用テキストエリア
	
	
	// コンストラクタ
	public ReservationDialog(Frame owner, ReservationControl rc) {
		// スーパークラスであるDialogのコンストラクタを呼び出す
		super(owner, "新規予約", true);
		
		this.rc = rc;
		
		// 初期値キャンセルを設定
		canceled = true;
		
		//ログインID表示用のボックス生成
		tfLoginID = new TextField("ログインID:"+rc.reservationUserID, 20);
		tfLoginID.setEditable(false);
		
		//日付表示用のボックス生成
		tfDate = new TextField("",12);
		tfDate.setEditable(false);
		
		// 教室選択ボックスの作成
		List<String> facilityId = new ArrayList<String>();
		facilityId = rc.getFacilityId();
		choiceFacility = new ChoiceFacility(facilityId);
		
		// 開始時刻(時分)選択ボックスの生成
		startHour    = new ChoiceHour();
		startMinute  = new ChoiceMinute();
		// 終了時刻(時分)選択ボックスの生成
		endHour      = new ChoiceHour();
		endMinute    = new ChoiceMinute();
		
		// ボタンの生成
		LogoutButton = new Button("ログアウト");
		buttonOK     = new Button("予約");
		prevButton = new Button("<");
        nextButton = new Button(">");
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
		ButtonArea = new TextArea(20,105);
		ButtonArea.setEditable(false);
		
		
		
		// 上部パネルに教室選択ボックス、年月日入力欄を配置
		//上部パネルの上パネルに「教室予約システム」というラベルと「ログイン」ボタンを追加
		panelNorthSub1.add(tfLoginID);
		panelNorthSub1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelNorthSub1.add(LogoutButton);
		panelNorthSub2.add(prevButton);
		panelNorthSub2.add(tfDate);
		panelNorthSub2.add(nextButton);
		panelNorthSub2.add(new Label("予約開始時刻"));
		panelNorthSub2.add(startHour);
		panelNorthSub2.add(new Label("時"));
		panelNorthSub2.add(startMinute);
		panelNorthSub2.add(new Label("分"));
		panelNorthSub2.add(new Label("予約終了時刻"));
		panelNorthSub2.add(endHour);
		panelNorthSub2.add(new Label("時"));
		panelNorthSub2.add(endMinute);
		panelNorthSub2.add(new Label("分"));
		panelNorthSub2.add(buttonOK);
		panelNorthSub2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelNorthSub3.add(new Label("予約可能教室"));
		panelNorthSub3.setFont(new Font(getFont().getName(), Font.BOLD, 16));
		panelNorthSub3.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		//中央パネル
		panelCenter.add(ButtonArea);
		
		
		// ReservationDialogをBorderLayoutに設定し、３つのパネルを追加
		setLayout(new BorderLayout());
		add(panelNorth, BorderLayout.NORTH);
		add(panelCenter, BorderLayout.CENTER);
		
		// Window Listenerを追加
		addWindowListener(this);
		// ボタンにアクションリスナを追加
		buttonOK.addActionListener(this);
		
		//日付変更処理
		prevButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeDate(-1);
            }
        });

        nextButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeDate(1);
            }
        });
        
        updateDateTextField(new Date());

		// 教室選択ボックス
		choiceFacility.addItemListener(this);
		startHour.addItemListener(this);
		endHour.addItemListener(this);
		
		// 選択された教室の、利用可能時刻の範囲を設定
		resetTimeRange(choiceFacility.getSelectedItem());
		
		// 大きさの設定、Windowのサイズ変更不可の設定
		this.setBounds(5, 5, 900, 455);
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
		if(e.getSource()== buttonOK) {
			canceled = false;
			setVisible(false);
			dispose();
		}
	}
	
	

    private void changeDate(int days) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, days);

        updateDateTextField(calendar.getTime());
    }

    public void updateDateTextField(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        tfDate.setText(year + "年" + month + "月" + day + "日");
    }
	
	
}
