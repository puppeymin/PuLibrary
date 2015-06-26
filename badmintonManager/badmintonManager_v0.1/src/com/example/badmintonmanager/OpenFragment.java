package com.example.badmintonmanager;

import java.util.Date;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DimensionRes;

import zrc.widget.ZrcListView;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import com.example.badmintonmanager.adapter.OpenItemAdapter;
import com.example.badmintonmanager.manager.MainManager;
import com.example.badmintonmanager.modelView.MainModel;
import com.example.db.MatchDBHelper;
import com.example.event.AddMatchCallbackEvent;
import com.example.event.MainMatchListItemClickAddEvent;
import com.example.event.MainPopFragmentStackEvent;
import com.example.view.PuPullToRefreshSlideListView;
import com.framework.base.BaseFragment;

import de.greenrobot.dao.Match;
import de.greenrobot.daoexample.dao.DaoMaster;
import de.greenrobot.daoexample.dao.DaoMaster.DevOpenHelper;
import de.greenrobot.daoexample.dao.DaoSession;
import de.greenrobot.daoexample.dao.MatchDao;
import de.greenrobot.daoexample.dao.MatchDao.Properties;
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
	public void onEventMainThread(AddMatchCallbackEvent evt){
		adapter.notifyDataSetChanged();
	}
}
