package com.example.badmintonmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.event.NavBarSelectEvent;
import com.framework.base.BaseFragment;
import com.framework.base.BaseNavActivity;

import de.greenrobot.event.EventBus;

public class MainActivity extends BaseNavActivity {
	
	private NavBarFragment navBarFragment;
	
	@Override
	public void onCreate(SharedPreferences sp, FragmentManager manager,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(sp, manager, savedInstanceState);
		
		EventBus.getDefault().register(this);
		
		navBarFragment = NavBarFragment_.builder().build();
		setNavView(navBarFragment);
//		addFirstToast("MainActivity");
	}

	@Override
	public void onDestroy() {
		// TODO 自动生成的方法存根
		super.onDestroy();

		EventBus.getDefault().unregister(this);
	}

	/////处理eventbus事件
	public void onEventMainThread(NavBarSelectEvent evt){
		switch (evt.getId()) {
		case R.id.rb_open:
			switchView(0,"open");
			break;
		case R.id.rb_manager:
			switchView(1,"manager");
			break;
		case R.id.rb_record:
			switchView(2,"record");
			break;
		default:
			break;
		}
	}
}
