package com.framework.base;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import com.framework.util.MyLogger;
/**
 * Fragment的基类
 * @author lee
 *		无需为view设置监听操作，只需要声明一个int[]类型的ids变量，把所有要监听的控件id放到该数组中即可，同时需要继承该类
 */
public abstract class BaseFragment extends Fragment implements OnTouchListener {
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
	public void onStart() {
		super.onStart();
		onViewCreated(getView());
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
	/**
	 * 界面创建完成之后
	 */
	public abstract void initData();
	
	// onTouch事件 将上层的触摸事件拦截
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
 
    public void onViewCreated(View view) {
        // 拦截触摸事件，防止泄露下去
    	view.setOnTouchListener(this);
    }
}
