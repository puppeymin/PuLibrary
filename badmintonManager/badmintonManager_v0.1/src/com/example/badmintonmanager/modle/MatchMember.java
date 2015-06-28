package com.example.badmintonmanager.modle;

public class MatchMember {
	private int id;
	private String name;
	private boolean isPay;
	
	public MatchMember(){};
	
	public MatchMember(int id, String name, boolean isPay){
		this.id = id;
		this.name = name;
		this.isPay = isPay;
	};

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isPay() {
		return isPay;
	}

	public void setPay(boolean isPay) {
		this.isPay = isPay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
