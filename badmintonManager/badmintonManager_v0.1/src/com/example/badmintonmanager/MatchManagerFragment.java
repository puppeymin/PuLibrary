package com.example.badmintonmanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;

import com.cengalabs.flatui.views.FlatButton;
import com.cengalabs.flatui.views.FlatEditText;
import com.example.badmintonmanager.adapter.MatchMemberItemAdapter;
import com.example.badmintonmanager.adapter.MatchMemberItemView;
import com.example.badmintonmanager.manager.MainManager;
import com.example.badmintonmanager.modle.MatchMember;
import com.example.event.DeleteMatchMemberEvent;
import com.example.event.MainPopFragmentStackEvent;
import com.example.event.SaveMatchEvent;
import com.example.event.UpdateMatchEvent;
import com.example.view.PuImageButton;
import com.framework.base.BaseFragment;
import com.framework.base.Toast;

import de.greenrobot.dao.Match;
import de.greenrobot.event.EventBus;

@EFragment(R.layout.fragment_match_manager)
public class MatchManagerFragment extends BaseFragment {

	@ViewById(R.id.pb_back) PuImageButton pbBack;
	@ViewById(R.id.et_time) FlatEditText etTime;
	@ViewById(R.id.et_manager) FlatEditText etManager;
	@ViewById(R.id.et_area) FlatEditText etArea;
	@ViewById(R.id.et_price) FlatEditText etPrice;
	@ViewById(R.id.fb_delect) FlatButton fbDelete;
	@ViewById(R.id.fb_save) FlatButton fbSave;
	@ViewById(R.id.fb_edit) FlatButton fbEdit;
	@ViewById(R.id.list_view) ListView listView;
	
	@FragmentArg
	Match matchData;
	
	private List<MatchMember> datas;
	private MatchMemberItemAdapter adapter;
	private boolean isEdit;
	private Calendar date; 
	private DatePickerDialog dialog;
	
	@Override
	public View onCreateView(LayoutInflater inflater, SharedPreferences sp,
			Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		return null;
	}
	
	@AfterInject
	public void init(){
		EventBus.getDefault().register(this);
	}

	@Override
	public void initData() {
		datas.addAll(MainManager.getInstance().getMatchMemberDates(matchData));
		adapter.notifyDataSetChanged();
	}

	@AfterViews
	void initView(){
		etTime.setText(matchData.getDateString());
		etArea.setText(matchData.getArea());
		etManager.setText(matchData.getManager());
		etPrice.setText(matchData.getPrice().toString());
		
		datas = new ArrayList<MatchMember>();
		adapter = new MatchMemberItemAdapter(getActivity(), datas);
		
		listView.setAdapter(adapter);
		
		Calendar calendar = Calendar.getInstance();

		DatePickerDialog.OnDateSetListener dateListener =   
				new DatePickerDialog.OnDateSetListener() {  
			@Override  
			public void onDateSet(DatePicker datePicker,   
					int year, int month, int dayOfMonth) {  
				//Calendar月份是从0开始,所以month要加1  
				etTime.setText(year + "年" +   
						(month+1) + "月" + dayOfMonth + "日");
				date.set(year, month, dayOfMonth);
			}  
		};  
		dialog = new DatePickerDialog(getActivity(),dateListener,calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
	}
	
	@Override
	public void onStop() {
		// TODO 自动生成的方法存根
		super.onStop();
		datas.clear();
	}
	
	@Override
	public void onDestroyView() {
		// TODO 自动生成的方法存根
		super.onDestroyView();
		EventBus.getDefault().unregister(this);
	}
	
	@Click(R.id.ll_time)
	public void chooseTime(View view){
		dialog.show();
	}
	
	@Click(R.id.pb_back)
	public void back(View view){
		EventBus.getDefault().post(new MainPopFragmentStackEvent());
	}
	
	@Click(R.id.fb_edit)
	void edit(View view){
		if(!isEdit){
			fbDelete.setVisibility(View.VISIBLE);
			fbEdit.setText("完成");
		}else{
			fbDelete.setVisibility(View.GONE);
			fbEdit.setText("编辑");
		}
		isEdit = !isEdit;
	}
	
	@Click(R.id.fb_delect)
	void delete(View view){
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < listView.getChildCount(); i++) {
			MatchMemberItemView m = (MatchMemberItemView)listView.getChildAt(i);
			if(m.isSelectDelete()){
				list.add(m.getMemberID());
			}
		}
		
		MainManager.getInstance().delectMember(matchData.getId(), list, true);
	}
	
	@Click(R.id.fb_save)
	void save(View view){
		boolean isUpdate = false;
		if(!matchData.getDateString().equals(etTime.getText().toString())){
			matchData.setTime(date.getTime());
			isUpdate = true;
		}
		if(!matchData.getManager().equals(etManager.getText().toString())){
			matchData.setManager(etManager.getText().toString());
			isUpdate = true;
		}
		if(!matchData.getArea().equals(etArea.getText().toString())){
			matchData.setArea(etArea.getText().toString());
			isUpdate = true;
		}
		if(!matchData.getPrice().equals(Integer.valueOf(etPrice.getText().toString()))){
			matchData.setPrice(Integer.parseInt(etPrice.getText().toString()));
			isUpdate = true;
		}
		String paidS = "";
		for (int i = 0; i < datas.size(); i++) {
			if(datas.get(i).isPay()){
				if(paidS != "") paidS += ",";
				paidS += datas.get(i).getName(); 
			}
		}
		if(matchData.getPaidMembers() != null && !matchData.getPaidMembers().equals(paidS)){
			matchData.setPaidMembers(paidS);
			isUpdate = true;
		}
		
		if(isUpdate) MainManager.getInstance().updatepMatchMember(matchData.getId(), true);
	}
	
	@Click(R.id.fb_complete)
	public void complete(View view){
		MainManager.getInstance().saveMatch(matchData.getId(), true);
	}
	
	//////////////////
	//////////////////处理eventbus事件
	public void onEventMainThread(DeleteMatchMemberEvent evt){
		datas.clear();
		datas.addAll(MainManager.getInstance().getMatchMemberDates(matchData));
		adapter.notifyDataSetChanged();
		Toast.showShort(getActivity(), "删除成功");
	}
	
	public void onEventMainThread(UpdateMatchEvent evt){
		Toast.showShort(getActivity(), "更新成功");
	}
	
	public void onEventMainThread(SaveMatchEvent evt){
		EventBus.getDefault().post(new MainPopFragmentStackEvent());
		Toast.showShort(getActivity(), "保存成功");
	}
	//////////////////////////
	
	public void setMatchData(Match matchData) {
		this.matchData = matchData;
	}
}
