package com.example.badmintonmanager;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;

import com.example.event.NavBarSelectEvent;
import com.framework.base.BaseFragment;

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
				int index = 0;
				switch (checkedId) {
				case R.id.rb_open:
					index = 0;
					break;
				case R.id.rb_manager:
					index = 1;
					break;
				case R.id.rb_record:
					index = 2;
					break;
				default:
					break;
				}
				EventBus.getDefault().post(new NavBarSelectEvent(index));
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, SharedPreferences sp,
			Bundle savedInstanceState) {
		return null;
	}

	@Override
	public void initData() {
		
	}
}
