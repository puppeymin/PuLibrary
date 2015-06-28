package com.example.badmintonmanager;

import java.util.ArrayList;

import org.androidannotations.annotations.AfterExtras;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterPreferences;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.event.AddMatchCallbackEvent;
import com.example.event.MainMatchListItemClickAddEvent;
import com.example.event.MainPopFragmentStackEvent;
import com.example.event.MainSwitchFragment;
import com.example.event.MainTitleClickAddEvent;
import com.example.event.NavBarSelectEvent;
import com.framework.base.BaseFragment;
import com.framework.base.BaseNavActivity;
import com.framework.util.ServiceUtil;

import de.greenrobot.event.EventBus;

@EActivity(R.layout.frag_base_nav)
public class MainActivity extends BaseNavActivity {

	private static final String ADD_MATCH = "addMatch";
	private static final String ADD_MEMBER = "addMember";
	private static final String MATCH_MEMBER = "matchMember";

	private NavBarFragment navBarFragment;
	private MainTitleFragment titleBarFragment;
	private AddMatchFragment addMatchFragment;
	private MatchManagerFragment matchManagerFragment;

	private int curIndex;
	private ArrayList<BaseFragment> fragments;
	private boolean idMainFragmentShow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		addFirstToast(".MainActivity_");

		///创建fragment
		navBarFragment = NavBarFragment_.builder().build();//底部
		titleBarFragment = MainTitleFragment_.builder().build();

		OpenFragment openFragment = OpenFragment_.builder().build();
		fragments = new ArrayList<BaseFragment>();
		fragments.add(openFragment);

		setActionTitle(titleBarFragment);
		setNavView(navBarFragment);
		setContentView(fragments);
	}

	@Override
	public void init() {
		EventBus.getDefault().register(this);
		//		getActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		initData();
	}

	private void initData() {

	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		EventBus.getDefault().unregister(this);
	}

	@AfterViews
	public void initView(){

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(idMainFragmentShow) popFragment();
			else return super.onKeyDown(keyCode, event);
		}
		return true;
	}

	/////////////////////////覆盖于主界面的fragment
	/**
	 * 回退fragment栈
	 */
	private void popFragment(){
		int d = getFragmentManager().getBackStackEntryCount();
		if(d <= 1){
			idMainFragmentShow = false;
		}
		getFragmentManager().popBackStack();
	}

	/**添加fragment
	 * 
	 * @param fragment
	 * @param tag
	 */
	private void addFragment(BaseFragment fragment, String tag){
		idMainFragmentShow = true;
		FragmentTransaction transaction = getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_left,
				R.animator.slide_right,
				R.animator.slide_left,
				R.animator.slide_right);
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
		idMainFragmentShow = false;
		FragmentTransaction transaction = getFragmentManager().beginTransaction().setCustomAnimations(R.animator.slide_left,
				R.animator.slide_right,
				R.animator.slide_left,
				R.animator.slide_right);
		transaction.remove(fragment);
		transaction.addToBackStack(null);
		//提交修改
		transaction.commit();
	}

	private void switchFragment(BaseFragment fragment,String TAG){
		if(fragment.isAdded()){
			removeFragment(fragment);
		}else{
			addFragment(fragment,TAG);
		}
		
		EventBus.getDefault().post(new MainSwitchFragment());
	}


	//////////////////////////////eventbus事件
	public void onEventMainThread(NavBarSelectEvent evt){
		curIndex = evt.getIndex();
		switchView(curIndex,"");
	}

	public void onEventMainThread(MainTitleClickAddEvent evt){
		switch (curIndex) {
		case 0:
			if(addMatchFragment == null) addMatchFragment = AddMatchFragment_.builder().build();
			switchFragment(addMatchFragment, ADD_MATCH);
			break;
		case 1:

			break;
		}
	}

	public void onEventMainThread(MainMatchListItemClickAddEvent evt){
		switch (curIndex) {
		case 0:
			if(matchManagerFragment == null)
			{
				matchManagerFragment = MatchManagerFragment_.builder()
						.matchData(evt.getMatch()).build();
			}else{
				matchManagerFragment.setMatchData(evt.getMatch());
			}
			switchFragment(matchManagerFragment, MATCH_MEMBER);
			break;
		}
	}

	public void onEventMainThread(MainPopFragmentStackEvent evt){
		popFragment();
	}
	/////////////////////////////

	////////////////////////////隐藏键盘
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			View v = getCurrentFocus();
			if (isShouldHideInput(v, ev)) {
				ServiceUtil.hideInput(this,v);
			}
			return super.dispatchTouchEvent(ev);
		}
		// 必不可少，否则所有的组件都不会有TouchEvent了
		if (getWindow().superDispatchTouchEvent(ev)) {
			return true;
		}
		return onTouchEvent(ev);
	}

	public  boolean isShouldHideInput(View v, MotionEvent event) {
		if (v != null && (v instanceof EditText)) {
			int[] leftTop = { 0, 0 };
			//获取输入框当前的location位置
			v.getLocationInWindow(leftTop);
			int left = leftTop[0];
			int top = leftTop[1];
			int bottom = top + v.getHeight();
			int right = left + v.getWidth();
			if (event.getX() > left && event.getX() < right
					&& event.getY() > top && event.getY() < bottom) {
				// 点击的是输入框区域，保留点击EditText的事件
				return false;
			} else {
				//清除焦点
				v.clearFocus();
				return true;
			}
		}
		return false;
	}
	/////////////////////////////
}
