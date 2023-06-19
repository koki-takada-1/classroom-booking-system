package client_system2;

import java.awt.*;
import java.util.List;

public class ChoiceFacility extends Choice{
	
	ChoiceFacility(List<String> facility){		//引数でリスト受け取る
		for( String id : facility) {			//facility_IDをidに１つずつ取り出す
			add( id);							//choiceにfacility_idを１つ追加
		}
	}
}
