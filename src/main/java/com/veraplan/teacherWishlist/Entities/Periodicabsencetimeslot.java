package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the periodicabsencetimeslot database table.
 * 
 */
@Entity
@NamedQuery(name="Periodicabsencetimeslot.findAll", query="SELECT p FROM Periodicabsencetimeslot p")
public class Periodicabsencetimeslot implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idPeriodicAbsenceTimeslot;

	private int timeSlotNumber;

	private byte weekday;

	//bi-directional many-to-one association to TeacherWishlist
	@ManyToOne
	@JoinColumn(name="Teacher_Wishlist_idTeacher_Wishlist")
	private TeacherWishlist teacherWishlist;

	public Periodicabsencetimeslot() {
	}

	public int getIdPeriodicAbsenceTimeslot() {
		return this.idPeriodicAbsenceTimeslot;
	}

	public void setIdPeriodicAbsenceTimeslot(int idPeriodicAbsenceTimeslot) {
		this.idPeriodicAbsenceTimeslot = idPeriodicAbsenceTimeslot;
	}

	public int getTimeSlotNumber() {
		return this.timeSlotNumber;
	}

	public void setTimeSlotNumber(int timeSlotNumber) {
		this.timeSlotNumber = timeSlotNumber;
	}

	public byte getWeekday() {
		return this.weekday;
	}

	public void setWeekday(byte weekday) {
		this.weekday = weekday;
	}

	public TeacherWishlist getTeacherWishlist() {
		return this.teacherWishlist;
	}

	public void setTeacherWishlist(TeacherWishlist teacherWishlist) {
		this.teacherWishlist = teacherWishlist;
	}

}