package com.example.badmintonmanager;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.cengalabs.flatui.views.FlatEditText;
import com.framework.base.BaseFragment;
import com.framework.base.Toast;
import com.framework.util.StringUtil;

@EFragment(R.layout.fragment_add_match)
public class AddMatchFragment extends BaseFragment {

	@ViewById FlatEditText et_time;
	@ViewById FlatEditText et_area;
	@ViewById FlatEditText et_manager;
	@ViewById FlatEditText et_member;
	
	@Override
	public View onCreateView(LayoutInflater inflater, SharedPreferences sp,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void initData() {
		
	}
	
	@Click(R.id.fb_save)
	public void onSave(View view){
		if(StringUtil.isEmpty(et_time.getText().toString())){
			Toast.showShort(getActivity(), "请输入时间");
		}
	}
	
	@Click(R.id.fb_complete)
	public void onComplete(View view){
		
	}
}
