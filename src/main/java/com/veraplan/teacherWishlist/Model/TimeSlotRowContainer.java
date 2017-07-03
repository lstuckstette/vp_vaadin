package com.veraplan.teacherWishlist.Model;

import java.util.ArrayList;
import java.util.List;

import com.veraplan.teacherWishlist.Entities.Periodicabsencetimeslot;

/**
 * TimeSlotRowContainer is representing a row in the periodic absence grid in EvaluationView.
 * @author Lukas Stuckstette
 */
public class TimeSlotRowContainer {

	private int timeSlotNumber;
	private TimeSlot monday, tuesday, wednesday, thursday, friday;

	/**
	 * constructor of TimeSlotRowContainer
	 * @param timeSlotNumber a number indicating the number of the timeslot, for example first or fifth timeslot of the day
	 */
	public TimeSlotRowContainer(int timeSlotNumber) {

		this.timeSlotNumber = timeSlotNumber;

		this.monday = new TimeSlot(1, timeSlotNumber);
		this.tuesday = new TimeSlot(2, timeSlotNumber);
		this.wednesday = new TimeSlot(3, timeSlotNumber);
		this.thursday = new TimeSlot(4, timeSlotNumber);
		this.friday = new TimeSlot(5, timeSlotNumber);

	}

	/**
	 * @return returns a formatted time-String representating the timeslotnumber associated with this TimeSlotRowContainer
	 */
	public String getTimeString() {

		return StaticSchoolData.getTimeString(this.timeSlotNumber);

	}

	/**
	 * @return returns the TimeSlot assiciated with monday
	 */
	public TimeSlot getMonday() {
		return monday;
	}

	/**
	 * @return returns the TimeSlot assiciated with tuesday
	 */
	public TimeSlot getTuesday() {
		return tuesday;
	}

	/**
	 * @return returns the TimeSlot assiciated with wednesday
	 */
	public TimeSlot getWednesday() {
		return wednesday;
	}

	/**
	 * @return returns the TimeSlot assiciated with thursday
	 */
	public TimeSlot getThursday() {
		return thursday;
	}

	/**
	 * @return returns the TimeSlot assiciated with friday
	 */
	public TimeSlot getFriday() {
		return friday;
	}

	/**
	 * @return returns a list containing all TimeSlots from monday till friday
	 */
	public List<TimeSlot> getTimeSlots() {
		ArrayList<TimeSlot> returnList = new ArrayList<>();
		returnList.add(this.getMonday());
		returnList.add(this.getTuesday());
		returnList.add(this.getWednesday());
		returnList.add(this.getThursday());
		returnList.add(this.getFriday());

		return returnList;
	}

	/**
	 * converts a list of TimeSlotRowContainer elements to a List of Periodicabsencetimeslot Entities
	 * @param inputList a list of TimeSlotRowContainer elements
	 * @return returns a List of Periodicabsencetimeslot Entities
	 */
	public static List<Periodicabsencetimeslot> toPeriodicabsencetimeslotList(List<TimeSlotRowContainer> inputList) {
		ArrayList<Periodicabsencetimeslot> returnList = new ArrayList<>();
		for (TimeSlotRowContainer tsrc : inputList) {
			for (TimeSlot ts : tsrc.getTimeSlots()) {
				if (ts.getSelected()) {
					returnList.add(ts.toPeriodicabsencetimeslotEntity());
				}
			}
		}
		return returnList;
	}

	
	/**
	 * converts a List of Periodicabsencetimeslot Entities toa list of TimeSlotRowContainer 
	 * @param inputList a List of Periodicabsencetimeslot Entities
	 * @return returns a list of TimeSlotRowContainer elements
	 */
	public static List<TimeSlotRowContainer> fromPeriodicabsencetimeslotList(List<Periodicabsencetimeslot> inputList) {
		ArrayList<TimeSlotRowContainer> resultList = new ArrayList<>();
		for (int i = 1; i <= StaticSchoolData.TIMESLOT_COUNT; i++) {
			TimeSlotRowContainer currentRow = new TimeSlotRowContainer(i);
			// check for marked slots:
			for (Periodicabsencetimeslot pats : inputList) {
				if (pats.getTimeSlotNumber() == i) {
					switch (pats.getWeekday()) {
					case 1:
						currentRow.getMonday().toggleSelected();
						break;
					case 2:
						currentRow.getTuesday().toggleSelected();
						break;
					case 3:
						currentRow.getWednesday().toggleSelected();
						break;
					case 4:
						currentRow.getThursday().toggleSelected();
						break;
					case 5:
						currentRow.getFriday().toggleSelected();
						break;
					default:
						break;
					}
				}
			}
			resultList.add(currentRow);
		}
		return resultList;
	}

}
