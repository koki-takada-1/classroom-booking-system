package client_system2;

import java.awt.Choice;

public class ChoiceHour extends Choice{
	// コンストラクタ
	ChoiceHour(){
		// 9から21を1から7に変更し、今後時間ではなく時間割制する予定
		resetRange(9, 21);
	}
	
	public void resetRange(int start, int end) {
		removeAll();
		while(start <= end) {
			String h = String.valueOf(start);
			if(h.length() == 1) {
				h = "0" + h;
			}
			add(h);
			start++;
		}
	}
	// 設定されている一番早い時刻を返すメソッド
	public String getFirst() {
		return getItem(0);
	}
	
	// 設定されている一番遅い時刻を返すメソッド
	public String getLast() {
		return getItem(getItemCount()-1);
	}
	
}
