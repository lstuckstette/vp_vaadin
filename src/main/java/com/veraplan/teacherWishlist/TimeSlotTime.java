package com.veraplan.teacherWishlist;

public class TimeSlotTime {

	private int minutes;
	private int hours;
	
	public TimeSlotTime(int hours,int minutes){
		this.minutes=minutes;
		this.hours=hours;
	}

	public int getMinutes() {
		return minutes;
	}

	public int getHours() {
		return hours;
	}
	
	
}
