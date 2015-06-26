package com.example.badmintonmanager.modelView;

import java.util.List;
import java.util.Set;

import com.MyAppcation;
import com.example.db.MatchDBHelper;
import com.example.db.MemberDBHelper;
import com.example.event.AddMatchCallbackEvent;
import com.example.event.AddMemberCallbackEvent;

import de.greenrobot.dao.Match;
import de.greenrobot.dao.Member;
import de.greenrobot.event.EventBus;
import android.content.Context;
import android.util.SparseArray;

public class MainModel {
	private List<Match> matchs;
	private List<Member> members;

	public MainModel(List<Match> matchs, List<Member> members){
		this.matchs = matchs;
		this.members = members;
		
		for (int i = 0; i < matchs.size(); i++) {
			format(matchs.get(i));
		}
	}

	/** 赋值完后调用*/
	public void format(Match match){
		//格式化日期字符串
		match.setDateString(String.format("%tF", match.getTime()));

		//格式化成员数组
		String[] names = match.getMenbers().split(",");
		for (int i = 0; i < names.length; i++) {
			
		}
		List<Member> list = MemberDBHelper.getInstance(MyAppcation.getInstance()).getMemberInfosByName(match.getMenbers());
		SparseArray<Member> sArray = listToSparseArray(list);

		match.setMenbersList(sArray);

		//格式化成员字符串
//		String ms = "";
//		if(match.getMenbers() != null){
//			for (int i = 0; i < sArray.size(); i++) {
//				if(i > 3){
//					break;
//				}
//				if(ms != "") ms += ",";
//				ms += sArray.valueAt(i).getName();
//			}
//		}
		match.setMenbersString(match.getMenbers());
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

	/**
	 * 添加新比赛
	 * @param match
	 * @param notify
	 */
	public void addNewMatch(Match match, boolean notify){
		MatchDBHelper.getInstance(MyAppcation.getInstance()).addToMatchInfoTable(match);
		////////处理新成员
		String[] members = match.getMenbers().split(",");
		for (int i = 0; i < members.length; i++) {
			Member member = new Member(null, members[i], 0, 0);
			addNewMember(member, false);
		}
		///////
		format(match);
		matchs.add(0, match);
		if(notify) EventBus.getDefault().post(new AddMatchCallbackEvent());
	}

	/**
	 * 添加新成员
	 * @param member
	 * @param notify
	 */
	public void addNewMember(Member member, boolean notify){
		////////处理新成员
		MemberDBHelper mh = MemberDBHelper.getInstance(MyAppcation.getInstance());
		if(!mh.isExist(member.getName())){
			mh.addToMemberInfoTable(member);
			members.add(member);
			if(notify) EventBus.getDefault().post(new AddMemberCallbackEvent());
		}
	}
}
