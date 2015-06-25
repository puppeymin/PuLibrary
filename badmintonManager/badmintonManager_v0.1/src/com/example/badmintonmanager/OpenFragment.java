package com.example.badmintonmanager;

import java.util.Date;
import java.util.List;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DimensionRes;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.badmintonmanager.adapter.OpenItemAdapter;
import com.example.db.MatchDBHelper;
import com.example.model.MatchModel;
import com.example.view.PuPullToRefreshSlideListView;
import com.framework.base.BaseFragment;

import de.greenrobot.dao.Match;
import de.greenrobot.daoexample.dao.DaoMaster;
import de.greenrobot.daoexample.dao.DaoMaster.DevOpenHelper;
import de.greenrobot.daoexample.dao.DaoSession;
import de.greenrobot.daoexample.dao.MatchDao;
import de.greenrobot.daoexample.dao.MatchDao.Properties;

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
	public void initData() {
		Match match = new Match(null, new Date(), "三号场", "炒饭", "2,5", "");
		
//		MatchDBHelper.getInstance(getActivity()).addToMatchInfoTable(match);
//		MatchDBHelper.getInstance(getActivity()).clearMatchInfo();
		list = MatchDBHelper.getInstance(getActivity()).getMatchInfoList();
		adapter = new OpenItemAdapter(getActivity(), list);
		
		for (int i = 0; i < list.size(); i++) {
			MatchModel.getInstance().format(getActivity(), list.get(i));
		}
		
		listView.setAdapter(adapter);
	}

	@AfterViews
	public void initView(){
		listView.setNeedAlignTop(false);
		listView.setSize(0, (int)(slideWidth));
	}
}
