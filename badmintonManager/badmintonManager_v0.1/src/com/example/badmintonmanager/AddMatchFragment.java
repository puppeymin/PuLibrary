package com.example.badmintonmanager;

import java.util.Calendar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.cengalabs.flatui.views.FlatEditText;
import com.example.badmintonmanager.manager.MainManager;
import com.example.event.AddMatchCallbackEvent;
import com.example.event.MainPopFragmentStackEvent;
import com.framework.base.BaseFragment;
import com.framework.base.Toast;
import com.framework.util.StringUtil;

import de.greenrobot.dao.Match;
import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_add_match)
public class AddMatchFragment extends BaseFragment {

	@ViewById FlatEditText et_time;
	@ViewById FlatEditText et_area;
	@ViewById FlatEditText et_manager;
	@ViewById FlatEditText et_member;
	@ViewById FlatEditText et_price;
	
	private DatePickerDialog dialog;
	private Calendar date; 

	@Override
	public View onCreateView(LayoutInflater inflater, SharedPreferences sp,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
	}

	@Override
	public void initData() {
		date = Calendar.getInstance();
	}
	
	@AfterInject
	public void initInject(){
		EventBus.getDefault().register(this);
	}

	@AfterViews
	public void initView(){
		Calendar calendar = Calendar.getInstance();

		DatePickerDialog.OnDateSetListener dateListener =   
				new DatePickerDialog.OnDateSetListener() {  
			@Override  
			public void onDateSet(DatePicker datePicker,   
					int year, int month, int dayOfMonth) {  
				//Calendar月份是从0开始,所以month要加1  
				et_time.setText(year + "年" +   
						(month+1) + "月" + dayOfMonth + "日");
				date.set(year, month, dayOfMonth);
			}  
		};  
		dialog = new DatePickerDialog(getActivity(),dateListener,calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
	}

	@Click(R.id.ll_time)
	public void chooseTime(View view){
		dialog.show();
	}

	@Click(R.id.pb_back)
	public void back(View view){
		EventBus.getDefault().post(new MainPopFragmentStackEvent());
	}

	@Click(R.id.fb_save)
	public void onSave(View view){
		if(StringUtil.isEmpty(et_time.getText().toString())){
			Toast.showShort(getActivity(), "请输入时间");
			return;
		}
		if(StringUtil.isEmpty(et_area.getText().toString())){
			Toast.showShort(getActivity(), "请输入场地号");
			return;
		}
		if(StringUtil.isEmpty(et_manager.getText().toString())){
			Toast.showShort(getActivity(), "请输入管理员名字");
			return;
		}
		if(StringUtil.isEmpty(et_member.getText().toString())){
			Toast.showShort(getActivity(), "请输入参与成员,用,分开");
			return;
		}
		if(StringUtil.isEmpty(et_price.getText().toString())){
			Toast.showShort(getActivity(), "请输入每人单价");
			return;
		}

		Match match = new Match(null, date.getTime(), et_area.getText().toString(), et_manager.getText().toString()
				, et_member.getText().toString(), null, Integer.valueOf(et_price.getText().toString()), false);

		MainManager.getInstance().addNewMatch(match,true);
	}
	
	
	///////////////////处理eventbus事件
	public void onEventMainThread(AddMatchCallbackEvent evt){
		EventBus.getDefault().post(new MainPopFragmentStackEvent());
	}
}
