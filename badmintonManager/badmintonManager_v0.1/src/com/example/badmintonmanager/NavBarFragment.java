package com.example.badmintonmanager;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.event.NavBarSelectEvent;
import com.framework.base.BaseFragment;
import com.framework.base.BaseNavActivity;

import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_nav)
public class NavBarFragment extends BaseFragment {
	@ViewById(R.id.rg_nav_bar) RadioGroup navBarGroup;

	public interface OnInitListener{
		public void init();
	}
	
	@AfterViews
	public void init(){
		navBarGroup.check(R.id.rb_open);
		navBarGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 自动生成的方法存根
				EventBus.getDefault().post(new NavBarSelectEvent(checkedId));
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, SharedPreferences sp,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		return null;
	}

	@Override
	public void findViews() {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void initData() {
		// TODO 自动生成的方法存根
		
	}
}
