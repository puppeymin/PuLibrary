package com.framework.base;
import java.util.List;

import com.example.badmintonmanager.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
/**
 * 浠orizontalScrollView+Fragment缁勫悎鐨刟ctivity
 * @author lee
 */
public class BaseSorllActivity extends BaseActivity {
	private List<BaseFragment> frags;
	private FragmentManager manager;
	@Override
	public void onCreate(SharedPreferences sp, FragmentManager manager,Bundle savedInstanceState) {
		this.manager = manager;
		setContentView(R.layout.activity_soll_base);
		findViewById(R.id.btn_allow);
	}
	/**
	 * 璁剧疆鏍囬鏍忚鍥�
	 * @param view
	 */
	public void setTitleView(View view){
		LinearLayout linear_nav = findViewToId(R.id.linear_nav);
		linear_nav.removeAllViews();
		linear_nav.addView(view);
	}
	/**
	 * 璁剧疆鍐呭瑙嗗浘
	 * @param frags
	 */
	public void setContentView(List<BaseFragment> frags){
		this.frags = frags;
		FragmentTransaction ft = manager.beginTransaction();
		ft.replace(R.id.frame_content, frags.get(0));
		ft.commit();
	}
	@Override
	public final void jumpFragment(BaseFragment frag, String tag) {
		Fragment fragment = manager.findFragmentByTag(tag);
		FragmentTransaction ft = manager.beginTransaction();
		if(fragment != null){
			ft.remove(fragment);
		}
		ft.replace(R.id.frame_content, frag, tag);
		if(tag != null){
			ft.addToBackStack(tag);
		}
		ft.commit();
	}
	/**
	 * 閫変腑鍐呭瑙嗗浘
	 * @param index 鍐呭瑙嗗浘鐨勭储寮�
	 * @param tag 鍐呭瑙嗗浘鐨勬爣璁�
	 */
	public void switchView(int index,String tag){
		BaseFragment frag = frags.get(index);
		jumpFragment(frag, tag);
	}
}