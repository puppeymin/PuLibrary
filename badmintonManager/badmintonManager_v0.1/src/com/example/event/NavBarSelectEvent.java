package com.example.event;

public class NavBarSelectEvent {
	private int index;//选择的索引
	
	public NavBarSelectEvent(int index){
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
