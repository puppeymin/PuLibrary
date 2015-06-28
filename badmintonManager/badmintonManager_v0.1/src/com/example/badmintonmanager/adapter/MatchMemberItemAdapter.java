package com.example.badmintonmanager.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.example.badmintonmanager.modle.MatchMember;
import com.framework.base.BaseAdapter;

import de.greenrobot.dao.Match;

public class MatchMemberItemAdapter extends BaseAdapter<MatchMember> {

	private Context context;
	private MatchMemberItemView matchMemberItemView;
	
	public MatchMemberItemAdapter(Context context,List<MatchMember> data) {
		super(data);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			matchMemberItemView = MatchMemberItemView_.build(context);
			convertView = matchMemberItemView;
		}else{
			matchMemberItemView = (MatchMemberItemView)convertView;
		}
		matchMemberItemView.bind(data.get(position));
		return convertView;
	}
}
