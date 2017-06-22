package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the absence database table.
 * 
 */
@Entity
@NamedQuery(name="Absence.findAll", query="SELECT a FROM Absence a")
public class Absence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idAbsence;

	private String comment;

	@Temporal(TemporalType.DATE)
	private Date end;

	@Temporal(TemporalType.DATE)
	private Date start;

	//bi-directional many-to-one association to TeacherWishlist
	@ManyToOne
	@JoinColumn(name="Teacher_Wishlist_idTeacher_Wishlist")
	private TeacherWishlist teacherWishlist;

	public Absence() {
	}

	public int getIdAbsence() {
		return this.idAbsence;
	}

	public void setIdAbsence(int idAbsence) {
		this.idAbsence = idAbsence;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Date getEnd() {
		return this.end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public Date getStart() {
		return this.start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public TeacherWishlist getTeacherWishlist() {
		return this.teacherWishlist;
	}

	public void setTeacherWishlist(TeacherWishlist teacherWishlist) {
		this.teacherWishlist = teacherWishlist;
	}

}