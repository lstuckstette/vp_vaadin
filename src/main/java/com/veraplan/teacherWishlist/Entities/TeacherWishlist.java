package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the teacher_wishlist database table.
 * 
 */
@Entity
@Table(name="teacher_wishlist")
@NamedQuery(name="TeacherWishlist.findAll", query="SELECT t FROM TeacherWishlist t")
public class TeacherWishlist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idTeacher_Wishlist;

	private Timestamp date;

	private String periodicAbsenceComment;

	//bi-directional many-to-one association to Absence
	@OneToMany(mappedBy="teacherWishlist")
	private List<Absence> absences;

	//bi-directional many-to-one association to Periodicabsencetimeslot
	@OneToMany(mappedBy="teacherWishlist")
	private List<Periodicabsencetimeslot> periodicabsencetimeslots;

	//bi-directional many-to-one association to Teacher
	@ManyToOne
	private Teacher teacher;

	public TeacherWishlist() {
	}

	public int getIdTeacher_Wishlist() {
		return this.idTeacher_Wishlist;
	}

	public void setIdTeacher_Wishlist(int idTeacher_Wishlist) {
		this.idTeacher_Wishlist = idTeacher_Wishlist;
	}

	public Timestamp getDate() {
		return this.date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getPeriodicAbsenceComment() {
		return this.periodicAbsenceComment;
	}

	public void setPeriodicAbsenceComment(String periodicAbsenceComment) {
		this.periodicAbsenceComment = periodicAbsenceComment;
	}

	public List<Absence> getAbsences() {
		return this.absences;
	}

	public void setAbsences(List<Absence> absences) {
		this.absences = absences;
	}

	public Absence addAbsence(Absence absence) {
		getAbsences().add(absence);
		absence.setTeacherWishlist(this);

		return absence;
	}

	public Absence removeAbsence(Absence absence) {
		getAbsences().remove(absence);
		absence.setTeacherWishlist(null);

		return absence;
	}

	public List<Periodicabsencetimeslot> getPeriodicabsencetimeslots() {
		return this.periodicabsencetimeslots;
	}

	public void setPeriodicabsencetimeslots(List<Periodicabsencetimeslot> periodicabsencetimeslots) {
		this.periodicabsencetimeslots = periodicabsencetimeslots;
	}

	public Periodicabsencetimeslot addPeriodicabsencetimeslot(Periodicabsencetimeslot periodicabsencetimeslot) {
		getPeriodicabsencetimeslots().add(periodicabsencetimeslot);
		periodicabsencetimeslot.setTeacherWishlist(this);

		return periodicabsencetimeslot;
	}

	public Periodicabsencetimeslot removePeriodicabsencetimeslot(Periodicabsencetimeslot periodicabsencetimeslot) {
		getPeriodicabsencetimeslots().remove(periodicabsencetimeslot);
		periodicabsencetimeslot.setTeacherWishlist(null);

		return periodicabsencetimeslot;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}