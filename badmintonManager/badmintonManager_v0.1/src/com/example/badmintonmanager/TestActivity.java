package com.example.badmintonmanager;

import java.util.List;

import zrc.widget.ZrcListView;

import com.example.badmintonmanager.adapter.OpenItemAdapter;
import com.example.badmintonmanager.adapter.TestItemAdapter;
import com.example.badmintonmanager.manager.MainManager;
import com.example.event.MainMatchListItemClickAddEvent;
import com.example.view.PuPullToRefreshSlideListView;

import de.greenrobot.dao.Match;
import de.greenrobot.event.EventBus;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class TestActivity extends Activity {

	private PuPullToRefreshSlideListView list;
	private List<Match> data;
	private TestItemAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);

		data = MainManager.getInstance().getMatchs();

		adapter = new TestItemAdapter(this, data);

		list = (PuPullToRefreshSlideListView) findViewById(R.id.list_view);
		list.setAdapter(adapter);

		list.setNeedAlignTop(false);
		list.setSize(0, 90);

		list.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
			@Override
			public void onItemClick(ZrcListView zrcListView, View view, int i, long l) {
				EventBus.getDefault().post(new MainMatchListItemClickAddEvent(data.get(i)));
			}
		});
	}
}
