package com.example.badmintonmanager.modelView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.MyAppcation;
import com.example.badmintonmanager.modle.MatchMember;
import com.example.db.MatchDBHelper;
import com.example.db.MemberDBHelper;
import com.example.event.AddMatchCallbackEvent;
import com.example.event.AddMemberCallbackEvent;
import com.example.event.DeleteMatchEcent;
import com.example.event.DeleteMatchMemberEvent;
import com.example.event.SaveMatchEvent;
import com.example.event.UpdateMatchEvent;
import com.framework.util.StringUtil;

import de.greenrobot.dao.Match;
import de.greenrobot.dao.Member;
import de.greenrobot.event.EventBus;
import android.content.Context;
import android.nfc.tech.IsoDep;
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
		String[] names = match.getMembers().split(",");
		for (int i = 0; i < names.length; i++) {
			
		}
		List<Member> list = MemberDBHelper.getInstance(MyAppcation.getInstance()).getMemberInfosByName(match.getMembers());
		SparseArray<Member> sArray = listToSparseArray(list);

		match.setMembersList(sArray);

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
		match.setMembersString(match.getMembers());
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
		String[] members = match.getMembers().split(",");
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
	
	/**
	 * 返回比赛活动的成员列表
	 * @param match
	 * @return
	 */
	public List<MatchMember> getMatchMemberDates(Match match){
		SparseArray<Member> data1 = match.getMembersList();
		List<MatchMember> data2 = new ArrayList<MatchMember>();
		for (int i = 0; i < data1.size(); i++) {
			MatchMember matchMember = new MatchMember(data1.valueAt(i).getId().intValue(), data1.valueAt(i).getName(),false);
			if(match.getPaidMembers() != null && match.getPaidMembers().indexOf(data1.valueAt(i).getName()) >= 0){
				matchMember.setPay(true);
			}
			data2.add(matchMember);
		}
		return data2;
	}
	
	/**
	 * 删除活动成员
	 * @param match
	 * @param ids
	 */
	public void delectMember(Long Id, List<Integer> ids, boolean notify){
		Match match = findMtach(Id);
		SparseArray<Member> sparseArray = match.getMembersList();
		String s = "";
		if(sparseArray != null){
			for (int i = 0; i < ids.size(); i++) {
				int id = ids.get(i).intValue();
				sparseArray.remove(id);
			}
			
			for (int i = 0; i < sparseArray.size(); i++) {
				if(s != "") s += ",";
				s += sparseArray.valueAt(i).getName();
			}
		}
		match.setMembers(s);
		format(match);
		MatchDBHelper.getInstance(MyAppcation.getInstance()).updateMatchInfo(match);
		if(notify) EventBus.getDefault().post(new DeleteMatchMemberEvent());
	}
	
	/**
	 * 更新数据
	 * @param match
	 */
	public void updateMatch(Long Id, boolean notify){
		Match match = findMtach(Id);
		updateMatch(match, notify);
	}
	
	/**
	 * 更新数据
	 * @param match
	 */
	public void updateMatch(Match match, boolean notify){
		format(match);
		MatchDBHelper.getInstance(MyAppcation.getInstance()).updateMatchInfo(match);
		if(notify) EventBus.getDefault().post(new UpdateMatchEvent());
	}
	
	/**
	 * 更新数据
	 * @param match
	 */
	public void saveMatch(Long Id, boolean notify){
		Match match = findMtach(Id);
		match.setIsComplete(true);
		updateMatch(match, false);
		matchs.remove(match);
		if(notify) EventBus.getDefault().post(new SaveMatchEvent());
	}

	/**
	 * 删除一场比赛活动
	 * @param id
	 * @param notify
	 */
	public void delectMatch(Long id, boolean notify) {
		MatchDBHelper.getInstance(MyAppcation.getInstance()).deleteMatchInfoList(id.intValue());
		matchs.remove(findMtach(id));
		if(notify) EventBus.getDefault().post(new DeleteMatchEcent());
	}
	
	private Match findMtach(Long id){
		for (int i = 0; i < matchs.size(); i++) {
			if(matchs.get(i).getId().equals(id)){
				return matchs.get(i);
			}
		}
		return null;
	}
}
