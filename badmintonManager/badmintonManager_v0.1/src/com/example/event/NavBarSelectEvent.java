package com.example.event;

public class NavBarSelectEvent {
	private int id;//�����button id
	
	public NavBarSelectEvent(int id){
		this.id = id;
	}

	public int getId() {
		return id;
	}
}
