package com.veraplan.teacherWishlist;

public final class StaticSchoolData {

	static final int TIMESLOT_COUNT = 9;

	private StaticSchoolData() {

	}
	// src: http://www.hhg-du.de/Stundenplanzeiten.188.0.html

	static TimeSlotTime getTimeSlotStartTime(int timeSlotNumber) {
		switch (timeSlotNumber) {
		case 1:
			return new TimeSlotTime(8, 0);
		case 2:
			return new TimeSlotTime(8, 50);
		// kleine Pause
		case 3:
			return new TimeSlotTime(9, 50);
		case 4:
			return new TimeSlotTime(10, 40);
		// grosse Pause
		case 5:
			return new TimeSlotTime(11, 45);
		case 6:
			return new TimeSlotTime(12, 35);
		case 7:
			return new TimeSlotTime(13, 35);// = Mittagspause
		case 8:
			return new TimeSlotTime(14, 25);
		case 9:
			return new TimeSlotTime(15, 10);
		default:
			return new TimeSlotTime(-1, -1);
		}

	}

	static TimeSlotTime getTimeSlotEndTime(int timeSlotNumber) {
		switch (timeSlotNumber) {
		case 1:
			return new TimeSlotTime(8, 45);
		case 2:
			return new TimeSlotTime(9, 35);
		// kleine Pause
		case 3:
			return new TimeSlotTime(10, 35);
		case 4:
			return new TimeSlotTime(11, 25);
		// grosse Pause
		case 5:
			return new TimeSlotTime(12, 30);
		case 6:
			return new TimeSlotTime(13, 20);
		case 7:
			return new TimeSlotTime(14, 20);// = Mittagspause
		case 8:
			return new TimeSlotTime(15, 10);
		case 9:
			return new TimeSlotTime(15, 55);
		default:
			return new TimeSlotTime(-1, -1);
		}

	}

	static String getTimeString(int timeSlotNumber) {
		if(timeSlotNumber > TIMESLOT_COUNT || timeSlotNumber < 1){
			return "error on TimeSlot.";
		}
		// format TimeStrings with leading zero's:
		TimeSlotTime startTime = StaticSchoolData.getTimeSlotStartTime(timeSlotNumber);
		TimeSlotTime endTime = StaticSchoolData.getTimeSlotEndTime(timeSlotNumber);

		String startHour = startTime.getHours() < 10 ? "0" + startTime.getHours() : "" + startTime.getHours();
		String startMinute = startTime.getMinutes() < 10 ? "0" + startTime.getMinutes() : "" + startTime.getMinutes();

		String endHour = endTime.getHours() < 10 ? "0" + endTime.getHours() : "" + endTime.getHours();
		String endMinute = endTime.getMinutes() < 10 ? "0" + endTime.getMinutes() : "" + endTime.getMinutes();

		return "" + startHour + ":" + startMinute + " - " + endHour + ":" + endMinute;
	}
}
