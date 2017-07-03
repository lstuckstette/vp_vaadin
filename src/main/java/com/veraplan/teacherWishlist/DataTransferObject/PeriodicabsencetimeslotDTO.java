package com.veraplan.teacherWishlist.DataTransferObject;

import java.io.Serializable;

/**
 * The data transfer class for the Periodicabsencetimeslot entity.
 * 
 * @author Lukas Stuckstette 
 */
public class PeriodicabsencetimeslotDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -751506736417790560L;

	private int id;
	private int weekday;
	private int timeslotnumber;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getWeekday() {
		return weekday;
	}

	public void setWeekday(int weekday) {
		this.weekday = weekday;
	}

	public int getTimeslotnumber() {
		return timeslotnumber;
	}

	public void setTimeslotnumber(int timeslotnumber) {
		this.timeslotnumber = timeslotnumber;
	}

}
