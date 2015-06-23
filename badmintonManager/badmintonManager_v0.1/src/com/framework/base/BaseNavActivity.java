package com.framework.base;

import java.util.List;

import com.example.badmintonmanager.R;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
/**
 * Fragment+搴曢儴瀵艰埅鐨勫熀绫�
 * @author lee
 */
public abstract class BaseNavActivity extends BaseActivity {
	private FragmentManager manager;
	private List<BaseFragment> frags;
	private FrameLayout fl_nav;
	@Override
	public void onCreate(SharedPreferences sp, FragmentManager manager, Bundle savedInstanceState) {
		setContentView(R.layout.frag_base_nav);
		this.manager = manager;
	}
	/**
	 * 璁剧疆瀵艰埅瑙嗗浘
	 * @param view
	 */
	public void setNavView(Fragment fragment){
//		fl_nav = findViewToId(R.id.linear_nav);
		FragmentTransaction ft = manager.beginTransaction();
		ft.add(R.id.linear_nav,fragment);
		ft.commit();
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
	 * @param tag  鍐呭瑙嗗浘鐨勬爣璁�
	 */
	public void switchView(int index,String tag){
		BaseFragment frag = frags.get(index);
		jumpFragment(frag, tag);
	}
	/**
	 * 闅愯棌搴曢儴瀵艰埅
	 */
	public void hiddenNav(){
		fl_nav.setVisibility(View.GONE);
	}
}
