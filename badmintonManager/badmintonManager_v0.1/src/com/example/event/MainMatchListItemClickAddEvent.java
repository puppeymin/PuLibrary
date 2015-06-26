package com.example.event;

import de.greenrobot.dao.Match;

public class MainMatchListItemClickAddEvent {
	private Match match;
	
	public MainMatchListItemClickAddEvent(Match match){
		this.match = match;
	}

	public Match getMatch() {
		return match;
	}
}
