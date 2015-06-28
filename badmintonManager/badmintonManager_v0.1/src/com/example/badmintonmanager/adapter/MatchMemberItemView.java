package com.example.badmintonmanager.adapter;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import com.cengalabs.flatui.views.FlatCheckBox;
import com.example.badmintonmanager.R;
import com.example.badmintonmanager.modle.MatchMember;

import de.greenrobot.dao.Match;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

@EViewGroup(R.layout.list_item_match_member)
public class MatchMemberItemView extends LinearLayout {
	
	@ViewById(R.id.fc_delect) FlatCheckBox fcDelete;
	@ViewById(R.id.tv_name) TextView tvName;
	@ViewById(R.id.fc_pay) FlatCheckBox fcPay;
	
	private MatchMember matchMember;

	public MatchMemberItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MatchMemberItemView(Context context) {
		super(context);
	}
	
	public void bind(MatchMember matchMember) {
		this.matchMember = matchMember;
		tvName.setText(matchMember.getName());
		fcPay.setChecked(matchMember.isPay());
    }
	
	public String getMemberName(){
		if(matchMember != null){
			return matchMember.getName();
		}
		return "";
	}
	
	public int getMemberID(){
		if(matchMember != null){
			return matchMember.getId();
		}
		return 0;
	}
	
	public boolean isSelectDelete(){
		return fcDelete.isChecked();
	}
}
