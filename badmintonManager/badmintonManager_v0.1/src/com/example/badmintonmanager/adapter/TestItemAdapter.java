package com.example.badmintonmanager.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.badmintonmanager.R;
import com.framework.base.BaseAdapter;

import de.greenrobot.dao.Match;

public class TestItemAdapter extends BaseAdapter<Match> {
	private Context context;
	
	public TestItemAdapter(Context context, List<Match> data) {
		super(data);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoder viewHoder = null;
		if(convertView == null){
			viewHoder = new ViewHoder();
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.test_item, null);
			
			convertView.setTag(viewHoder);
		}else{
			viewHoder = (ViewHoder)convertView.getTag();
		}
		
		return convertView;
	}
		

	class ViewHoder{
		public TextView tvDate;
		public TextView tvArea;
		public TextView tvManager;
		public TextView tvMember;
		public Button btnDelect;
	}
}