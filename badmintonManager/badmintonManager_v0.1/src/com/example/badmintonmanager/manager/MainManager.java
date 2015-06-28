package com.example.badmintonmanager.manager;

import java.util.List;

import android.R.integer;

import com.MyAppcation;
import com.example.badmintonmanager.modelView.MainModel;
import com.example.badmintonmanager.modle.MatchMember;
import com.example.db.MatchDBHelper;
import com.example.db.MemberDBHelper;
import com.example.event.AddMatchCallbackEvent;
import com.example.event.AddMemberCallbackEvent;

import de.greenrobot.dao.Match;
import de.greenrobot.dao.Member;
import de.greenrobot.event.EventBus;

public class MainManager {

	private static MainManager instance;

	private List<Match> matchs;
	private List<Member> members;
	
	private MainModel mainModel;

	public MainManager(){
	}

	// 初始化
	public void init() {
		matchs = MatchDBHelper.getInstance(MyAppcation.getInstance()).getMatchInfoList();
		members = MemberDBHelper.getInstance(MyAppcation.getInstance()).getMemberInfo();
		
		mainModel = new MainModel(matchs, members);
	}

	public static MainManager getInstance() {
		if(instance == null) instance = new MainManager();
		return instance;
	}

	public void addNewMatch(Match match, boolean notify){
		mainModel.addNewMatch(match, notify);
	}

	public void addNewMember(Member member, boolean notify){
		mainModel.addNewMember(member, notify);
	}
	
	public List<MatchMember> getMatchMemberDates(Match match){
		return mainModel.getMatchMemberDates(match);
	}
	
	public void delectMember(Long Id, List<Integer> deleteIds, boolean notify){
		mainModel.delectMember(Id, deleteIds, notify);
	}
	
	public void updatepMatchMember(Long Id, boolean notify){
		mainModel.updateMatch(Id, notify);
	}
	
	public void saveMatch(Long Id, boolean notify){
		mainModel.saveMatch(Id, notify);
	}

	public List<Match> getMatchs() {
		return matchs;
	}

	public void delectMatch(Long id, boolean notify) {
		mainModel.delectMatch(id, notify);
	}
}
