package com.framework.base;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import com.framework.manager.PagerManager;
import com.framework.util.MyLogger;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.AbsoluteLayout;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
/**
 * Fragment鐨勫熀绫�
 * @author lee
 *		鏃犻渶涓簐iew璁剧疆鐩戝惉鎿嶄綔锛屽彧闇�瑕佸０鏄庝竴涓猧nt[]绫诲瀷鐨刬ds鍙橀噺锛屾妸鎵�鏈夎鐩戝惉鐨勬帶浠秈d鏀惧埌璇ユ暟缁勪腑鍗冲彲锛屽悓鏃堕渶瑕佺户鎵胯绫�
 */
public abstract class BaseFragment extends Fragment implements OnClickListener, OnLongClickListener, OnItemClickListener, OnItemLongClickListener, OnItemSelectedListener {
	private SharedPreferences sp;
	public FragmentManager manager;
	public View view;
	public MyLogger Log;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		Log = MyLogger.kLog();
		manager = getFragmentManager();
		sp = getActivity().getSharedPreferences(SPContants.CONFIG, Context.MODE_PRIVATE);
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState){
		view = onCreateView(inflater, sp,savedInstanceState);
		return view;
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		findViews();
	}
	@Override
	public void onStart() {
		super.onStart();
//		setListener();
	}
	@Override
	public void onResume() {
		super.onResume();
		initData();
	}
	@Override
	public void onDestroy() {
		BaseAppcation.getInstance().clearAsyncTask();
		super.onDestroy();
	}
	
	public abstract View onCreateView(LayoutInflater inflater,SharedPreferences sp,Bundle savedInstanceState);
	public abstract void findViews();
	public abstract void initData();
	public <T extends View> T findViewById(int resId){
		return (T) view.findViewById(resId);
	}
	@Override
	public void onClick(View v) {
	}
	@Override
	public boolean onLongClick(View v) {
		return false;
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
	}
	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		return false;
	}
	/**
	 * Spinner鎺т欢鐨勪簨浠剁洃鍚�
	 */
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
	}
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}
	/**
	 * 鏄剧ずPager瑙嗗浘,涓�鏃﹀惎鍔ㄨ繖绉嶅姞杞芥ā寮忥紝浼氶檷浣庤鍥惧眰瀵瑰唴瀛樼殑浣跨敤鐜囷紝鍙互鏇村ソ鐨勪紭鍖栧唴瀛�
	 * @param pager
	 */
	public void showPager(BasePager pager,String tag){
		View child  = pager.getView();
		if(child != null){
			//page椤甸潰瑙嗗浘View鐨剆etTag()鏂规硶琚崰鐢ㄤ簡銆備互渚夸簬瀵绘壘褰撳墠鏄剧ず椤�
			child.setTag(pager);
			if(view instanceof ViewGroup){
				ViewGroup parent = (ViewGroup)view;
				parent.removeAllViews();
				parent.addView(child);
			}
		}
	}
	/**
	 * 鑾峰彇褰撳墠姝ｅ湪鏄剧ずPage椤甸潰锛屼互渚垮鐞嗚繑鍥為敭鐨勪簨浠朵紶閫掞紝濡傛灉鑾峰彇涓嶅埌灏嗚繑鍥瀗ull
	 * @return
	 */
	public BasePager getPager(){
		if(view != null && view instanceof ViewGroup){
			ViewGroup group = (ViewGroup) view;
			Object obj = group.getChildAt(0).getTag();
			if(obj != null && obj instanceof BasePager){
				return (BasePager) obj;
			}
		}
		return null;
	}
}
