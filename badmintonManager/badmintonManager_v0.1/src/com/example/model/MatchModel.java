package com.example.model;

import java.util.List;
import java.util.Set;

import com.example.db.MemberDBHelper;

import de.greenrobot.dao.Match;
import de.greenrobot.dao.Member;
import android.content.Context;
import android.util.SparseArray;

public class MatchModel {
	
	private static MatchModel instance;
	
	public static MatchModel getInstance(){
		if(instance == null) instance = new MatchModel();
		return instance;
	}

	/** 赋值完后调用*/
	public void format(Context context, Match match){
		//格式化日期字符串
		match.setDateString(String.format("%tF", match.getTime()));
		
		//格式化成员数组
		List<Member> list = MemberDBHelper.getInstance(context).getMemberInfoById(match.getMenbers());
		SparseArray<Member> sArray = listToSparseArray(list);
		
		match.setMenbersList(sArray);

		//格式化成员字符串
		String ms = "";
		if(match.getMenbers() != null){
			for (int i = 0; i < sArray.size(); i++) {
				if(i > 3){
					break;
				}
				if(ms != "") ms += ",";
				ms += sArray.valueAt(i).getName();
			}
		}
		match.setMenbersString(ms);
	}
	
	/**
     * 把成员List转化为SparseArray
     * 
     */
    
    public static SparseArray<Member> listToSparseArray(List<Member> list){
    	SparseArray<Member> s = new SparseArray<Member>(); 
    	for (int i = 0; i < list.size(); i++) {
			s.put(list.get(i).getId().intValue(), list.get(i));
		}
    	return s;
    }
}
