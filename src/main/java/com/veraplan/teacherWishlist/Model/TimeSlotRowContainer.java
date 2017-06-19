package com.veraplan.teacherWishlist.Model;

public class TimeSlotRowContainer {

	private int timeSlotNumber;
	private TimeSlot monday, tuesday, wednesday, thursday, friday;

	public TimeSlotRowContainer(int timeSlotNumber) {

		this.timeSlotNumber = timeSlotNumber;

		this.monday = new TimeSlot(1, timeSlotNumber);
		this.tuesday = new TimeSlot(2, timeSlotNumber);
		this.wednesday = new TimeSlot(3, timeSlotNumber);
		this.thursday = new TimeSlot(4, timeSlotNumber);
		this.friday = new TimeSlot(5, timeSlotNumber);

	}

	public String getTimeString() {

		return StaticSchoolData.getTimeString(this.timeSlotNumber);
		
	}

	public TimeSlot getMonday() {
		return monday;
	}

	public TimeSlot getTuesday() {
		return tuesday;
	}

	public TimeSlot getWednesday() {
		return wednesday;
	}

	public TimeSlot getThursday() {
		return thursday;
	}

	public TimeSlot getFriday() {
		return friday;
	}

}
