package com.veraplan.teacherWishlist.Model;

import java.util.ArrayList;
import java.util.List;

import com.veraplan.teacherWishlist.Entities.Periodicabsencetimeslot;

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

	public List<TimeSlot> getTimeSlots() {
		ArrayList<TimeSlot> returnList = new ArrayList<>();
		returnList.add(this.getMonday());
		returnList.add(this.getTuesday());
		returnList.add(this.getWednesday());
		returnList.add(this.getThursday());
		returnList.add(this.getFriday());

		return returnList;
	}

	public static List<Periodicabsencetimeslot> toPeriodicabsencetimeslotList(List<TimeSlotRowContainer> inputList){
		ArrayList<Periodicabsencetimeslot> returnList = new ArrayList<>();
		for(TimeSlotRowContainer tsrc : inputList){
			for(TimeSlot ts : tsrc.getTimeSlots()){
				if(ts.getSelected()){
					returnList.add(ts.toPeriodicabsencetimeslot());
				}
			}
		}
		return returnList;
	}

}
