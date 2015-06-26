package com.example.badmintonmanager.adapter;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import com.example.badmintonmanager.R;
import com.example.event.MainMatchListItemClickAddEvent;
import com.example.view.PuScrollLinerLayout;

import de.greenrobot.dao.Match;
import de.greenrobot.event.EventBus;

@EViewGroup(R.layout.list_item_open)
public class OpenItemView extends PuScrollLinerLayout {
	
	@ViewById(R.id.tv_date) TextView tvDate;
	@ViewById(R.id.tv_area) TextView tvArea;
	@ViewById(R.id.tv_manager) TextView tvManager;
	@ViewById(R.id.tv_member) TextView tvMember;
	private Match match;

	public OpenItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public OpenItemView(Context context) {
		super(context);
	}
	
//	@Click(R.id.rl_open)
//	public void openMatch(View view){
//		EventBus.getDefault().post(new MainMatchListItemClickAddEvent(match));
//	}
	
	@Click(R.id.btn_delect)
	public void delect(View view){
		
	}
	
	public void bind(Match match) {
		this.match = match;
		tvDate.setText(match.getDateString());
		tvArea.setText(match.getArea());
		tvManager.setText(match.getManager());
		tvMember.setText(match.getMenbersString());
    }
	
	@Override  
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {  
	      
	    int childCount = getChildCount();  
	    //设置该ViewGroup的大小  
	    int specSize_width = MeasureSpec.getSize(widthMeasureSpec);  
	    int specSize_height = MeasureSpec.getSize(heightMeasureSpec);
	    setMeasuredDimension(700, 80);  
	      
	    for (int i = 0; i < childCount; i++) {  
	        View childView = getChildAt(i);  
	        childView.measure(0, 0);  
	    }  
	}  
}
