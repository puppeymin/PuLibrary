package com.example.badmintonmanager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.event.MainTitleClickAddEvent;
import com.example.event.NavBarSelectEvent;
import com.framework.base.BaseFragment;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.title_main)
public class MainTitleFragment extends BaseFragment {

	@ViewById(R.id.tv_title) TextView tvTitle;
	@ViewById(R.id.iv_add)	ImageView ivAdd;

//	@FragmentArg("")
//	String title;
//
//	@FragmentArg
//	int ivAddVisible;

	private String[] titleStrings;
	private int[] addVisibleBools;

	@Override
	public View onCreateView(LayoutInflater inflater, SharedPreferences sp,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		return null;
	}

	@AfterInject
	public void initInject() {
		titleStrings = new String[]{"开场","管理","记录"};
		addVisibleBools = new int[]{View.VISIBLE,View.GONE,View.VISIBLE};

		EventBus.getDefault().register(this);
	}

	@Override
	public void initData() {
		// TODO 自动生成的方法存根
	}

	@AfterViews
	public void initView(){
		tvTitle.setText(titleStrings[0]);
		ivAdd.setVisibility(addVisibleBools[0]);
	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();

		EventBus.getDefault().unregister(this);
	}
	
	@Click(R.id.iv_add)
	public void onAddClick(View view){
		EventBus.getDefault().post(new MainTitleClickAddEvent());
	}

	//////////////////////////////eventbus事件
	public void onEventMainThread(NavBarSelectEvent evt){
		tvTitle.setText(titleStrings[evt.getIndex()]);
		ivAdd.setVisibility(addVisibleBools[evt.getIndex()]);
	}
}
