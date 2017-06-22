package com.veraplan.teacherWishlist.Model; 

public class TimeSlot {

	private int weekday;
	private int timeSlotNumber;
	private boolean selected;

	public TimeSlot(int weekday, int timeSlotNumber) {

		this.weekday = weekday;
		this.timeSlotNumber = timeSlotNumber;
		this.selected = false;

	}

	public int getWeekday() {
		return weekday;
	}

	public int getTimeSlotNumber() { 
		return timeSlotNumber;
	}

	public boolean getSelected() {
		return selected;
	}

	public void toggleSelected() {
		selected = !selected;
	}

	public String toString() {
		return selected ? "X" : "";
	}

	public String printInfo() {
		return "TS-Info: WeekDay '" + weekday + "' TimeString '" + StaticSchoolData.getTimeString(timeSlotNumber) + "'";
	}

}
