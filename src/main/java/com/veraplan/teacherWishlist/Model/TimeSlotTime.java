package com.veraplan.teacherWishlist.Model;
 
/**
 * TimeSlotTime is representing the start or end time of a TimeSlot
 * @author Lukas Stuckstette
 */
public class TimeSlotTime {

	private int minutes;
	private int hours;
	
	/**
	 * @param hours hours
	 * @param minutes minutes
	 */
	public TimeSlotTime(int hours,int minutes){
		this.minutes=minutes;
		this.hours=hours;
	}

	/**
	 * @return returns the minutes of the TimeSlotTime
	 */
	public int getMinutes() {
		return minutes;
	}

	/**
	 * @return returns the hours of the TimeSlotTime
	 */
	public int getHours() {
		return hours;
	}
	
	
}
