package com.example.badmintonmanager.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.badmintonmanager.R;
import com.framework.base.BaseAdapter;

import de.greenrobot.dao.Match;

public class OpenItemAdapter extends BaseAdapter<Match> {
	private Context context;
	
	public OpenItemAdapter(Context context, List<Match> data) {
		super(data);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoder viewHoder = null;
		if(convertView == null){
			viewHoder = new ViewHoder();
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.list_item_open, null);
			viewHoder.tvDate = (TextView)convertView.findViewById(R.id.tv_date);
			viewHoder.tvArea = (TextView)convertView.findViewById(R.id.tv_area);
			viewHoder.tvManager = (TextView)convertView.findViewById(R.id.tv_manager);
			viewHoder.tvMember = (TextView)convertView.findViewById(R.id.tv_member);
			
			convertView.setTag(viewHoder);
		}else{
			viewHoder = (ViewHoder)convertView.getTag();
		}
		
		viewHoder.tvDate.setText(data.get(position).getDateString());
		viewHoder.tvArea.setText(data.get(position).getArea());
		viewHoder.tvManager.setText(data.get(position).getManager());
		viewHoder.tvMember.setText(data.get(position).getMenbersString());
		
		return convertView;
	}
		

	class ViewHoder{
		public TextView tvDate;
		public TextView tvArea;
		public TextView tvManager;
		public TextView tvMember;
	}
}