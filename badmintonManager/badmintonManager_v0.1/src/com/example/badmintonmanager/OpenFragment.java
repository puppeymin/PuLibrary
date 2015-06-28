package com.example.badmintonmanager;

import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DimensionRes;

import zrc.widget.ZrcListView;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.badmintonmanager.adapter.OpenItemAdapter;
import com.example.badmintonmanager.manager.MainManager;
import com.example.event.AddMatchCallbackEvent;
import com.example.event.DeleteMatchEcent;
import com.example.event.DeleteMatchMemberEvent;
import com.example.event.MainMatchListItemClickAddEvent;
import com.example.event.MainSwitchFragment;
import com.example.event.SaveMatchEvent;
import com.example.event.UpdateMatchEvent;
import com.example.view.PuPullToRefreshSlideListView;
import com.framework.base.BaseFragment;

import de.greenrobot.dao.Match;
import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_open)
public class OpenFragment extends BaseFragment {

	@ViewById(R.id.list_view) PuPullToRefreshSlideListView listView;
	@DimensionRes(R.dimen.user_list_slide_width) float slideWidth;
	private List<Match> list;
	private OpenItemAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, SharedPreferences sp,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void initData() {
		list = MainManager.getInstance().getMatchs();
		adapter = new OpenItemAdapter(getActivity(), list);

		listView.setAdapter(adapter);
	}

	@AfterInject
	void initInject(){
		EventBus.getDefault().register(this);
	}

	@AfterViews
	void initView(){
		listView.setNeedAlignTop(false);
		listView.setSize(0, (int)(slideWidth));
		
		if (listView != null) {
			listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
	            @Override
	            public void onItemClick(ZrcListView zrcListView, View view, int i, long l) {
	                EventBus.getDefault().post(new MainMatchListItemClickAddEvent(list.get(i)));
	            }
	        });
        }
	}

	/////////////处理eventbus事件
	public void onEventMainThread(MainSwitchFragment evt){
		listView.resetItems(false);
	}
	
	public void onEventMainThread(AddMatchCallbackEvent evt){
		adapter.notifyDataSetChanged();
	}
	
	public void onEventMainThread(DeleteMatchMemberEvent evt){
		adapter.notifyDataSetChanged();
	}
	
	public void onEventMainThread(UpdateMatchEvent evt){
		adapter.notifyDataSetChanged();
	}
	
	public void onEventMainThread(SaveMatchEvent evt){
		adapter.notifyDataSetChanged();
	}
	
	public void onEventMainThread(DeleteMatchEcent evt){
		adapter.notifyDataSetChanged();
	}
}
