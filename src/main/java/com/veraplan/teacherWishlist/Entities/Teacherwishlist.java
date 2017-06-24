package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the teacherwishlist database table.
 * 
 */
@Entity
@NamedQuery(name="Teacherwishlist.findAll", query="SELECT t FROM Teacherwishlist t")
public class Teacherwishlist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTeacherWishlist;

	private Timestamp date;

	private String periodicAbsenceComment;

	//bi-directional many-to-one association to Absence
	@OneToMany(mappedBy="teacherwishlist")
	private List<Absence> absences;

	//bi-directional many-to-one association to Periodicabsencetimeslot
	@OneToMany(mappedBy="teacherwishlist")
	private List<Periodicabsencetimeslot> periodicabsencetimeslots;

	//bi-directional many-to-one association to Teacher
	@ManyToOne
	@JoinColumn(name="teacherFK")
	private Teacher teacher;

	public Teacherwishlist() {
	}

	public int getIdTeacherWishlist() {
		return this.idTeacherWishlist;
	}

	public void setIdTeacherWishlist(int idTeacherWishlist) {
		this.idTeacherWishlist = idTeacherWishlist;
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
		absence.setTeacherwishlist(this);

		return absence;
	}

	public Absence removeAbsence(Absence absence) {
		getAbsences().remove(absence);
		absence.setTeacherwishlist(null);

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
		periodicabsencetimeslot.setTeacherwishlist(this);

		return periodicabsencetimeslot;
	}

	public Periodicabsencetimeslot removePeriodicabsencetimeslot(Periodicabsencetimeslot periodicabsencetimeslot) {
		getPeriodicabsencetimeslots().remove(periodicabsencetimeslot);
		periodicabsencetimeslot.setTeacherwishlist(null);

		return periodicabsencetimeslot;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

}