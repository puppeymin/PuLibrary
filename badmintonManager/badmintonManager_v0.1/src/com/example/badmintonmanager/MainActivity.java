package com.example.badmintonmanager;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.event.NavBarSelectEvent;
import com.framework.base.BaseFragment;
import com.framework.base.BaseNavActivity;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.frag_base_nav)
public class MainActivity extends BaseNavActivity {

	@ViewById(R.id.tv_title) TextView tvTitle;
	@ViewById(R.id.iv_add)	ImageView ivAdd;

	private static final String ADD_MATCH = "addMatch";
	private static final String ADD_MEMBER = "addMember";
	
	private NavBarFragment navBarFragment;

	private String[] titleStrings;
	private int[] addVisibleBools;

	private int curIndex;
	private ArrayList<BaseFragment> fragments;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		navBarFragment = NavBarFragment_.builder().build();
		setNavView(navBarFragment);
		addFirstToast(".MainActivity_");
		
		fragments = new ArrayList<BaseFragment>();
		OpenFragment openFragment = OpenFragment_.builder().build(); 
		fragments.add(openFragment);
		setContentView(fragments);
	}

	private void initData() {
		titleStrings = new String[]{"开场","管理","记录"};
		addVisibleBools = new int[]{View.VISIBLE,View.GONE,View.VISIBLE};
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		EventBus.getDefault().unregister(this);
	}

	@AfterViews
	public void initView(){
		tvTitle.setText(titleStrings[0]);
		ivAdd.setVisibility(addVisibleBools[0]);
	}

	@Click(R.id.iv_add)
	public void onAdd(View view){
		switch (curIndex) {
		case 0:
			
			break;
		case 1:

			break;
		}
	}
	
	/////////////////////////覆盖于主界面的fragment
	/**添加fragment
	 * 
	 * @param fragment
	 * @param tag
	 */
	private void addFragment(BaseFragment fragment, String tag){
		FragmentTransaction transaction = getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up,
				R.animator.slide_down,
				R.animator.slide_up,
				R.animator.slide_down);
		transaction.add(R.id.frame_main, fragment, tag);
		transaction.addToBackStack(null);
		//提交修改
		transaction.commit();
	}
	
	/**移除fragment
	 * 
	 * @param fragment
	 */
	private void removeFragment(BaseFragment fragment){
		FragmentTransaction transaction = getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_up,
				R.animator.slide_down,
				R.animator.slide_up,
				R.animator.slide_down);
		transaction.remove(fragment);
		transaction.addToBackStack(null);
		//提交修改
		transaction.commit();
	}

	
	//////////////////////////////eventbus事件
	public void onEventMainThread(NavBarSelectEvent evt){
		curIndex = evt.getIndex();
		switchView(curIndex,"");
		tvTitle.setText(titleStrings[curIndex]);
		ivAdd.setVisibility(addVisibleBools[curIndex]);
	}

	@Override
	public void init() {
		EventBus.getDefault().register(this);
		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getActionBar().setCustomView(R.layout.title_main);
		
		initData();
	}
}
