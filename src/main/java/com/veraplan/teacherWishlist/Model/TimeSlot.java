package com.veraplan.teacherWishlist.Model;

import com.veraplan.teacherWishlist.Entities.Periodicabsencetimeslot;

/**
 * TimeSlotTime is representing a specific hour on the timetable
 * @author Lukas Stuckstette
 */
public class TimeSlot {

	private int weekday;
	private int timeSlotNumber;
	private boolean selected;

	/**
	 * constructor of TimeSlot
	 * @param weekday the timeslots weekday
	 * @param timeSlotNumber the timeslots number
	 */
	public TimeSlot(int weekday, int timeSlotNumber) {

		this.weekday = weekday;
		this.timeSlotNumber = timeSlotNumber;
		this.selected = false;

	}

	/**
	 * @return returns the timeslots weekday
	 */
	public int getWeekday() {
		return weekday;
	}

	/**
	 * @return returns the timeslots number
	 */
	public int getTimeSlotNumber() { 
		return timeSlotNumber;
	}

	/**
	 * @return returns weather the TimeSlot is selected
	 */
	public boolean getSelected() {
		return selected;
	}

	/**
	 * switches the timeslots selected state from true to false or false to true
	 */
	public void toggleSelected() {
		selected = !selected;
	}

	public String toString() {
		return selected ? "X" : "";
	}

	/**
	 * @return returns a string containing information about the timeslot
	 */
	public String printInfo() {
		return "TS-Info: WeekDay '" + weekday + "' TimeString '" + StaticSchoolData.getTimeString(timeSlotNumber) + "'";
	}
	
	/**
	 * converts the timeslot to a Periodicabsencetimeslot entity
	 * @return returns a Periodicabsencetimeslot entity
	 */
	public Periodicabsencetimeslot toPeriodicabsencetimeslotEntity(){
		Periodicabsencetimeslot pats = new Periodicabsencetimeslot();
		pats.setTimeSlotNumber(this.getTimeSlotNumber());
		pats.setWeekday(this.getWeekday());
		
		return pats;
	}

}
