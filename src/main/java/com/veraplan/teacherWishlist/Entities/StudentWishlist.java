package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the student_wishlist database table.
 * 
 */
@Entity
@Table(name="student_wishlist")
@NamedQuery(name="StudentWishlist.findAll", query="SELECT s FROM StudentWishlist s")
public class StudentWishlist implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idStudent_Wishlist;

	private String changeRoom;

	private String comment;

	private String preferredLessons;

	private String prefferedLessonTime;

	private Timestamp timestamp;

	//bi-directional many-to-one association to Student
	@ManyToOne
	private Student student;

	public StudentWishlist() {
	}

	public int getIdStudent_Wishlist() {
		return this.idStudent_Wishlist;
	}

	public void setIdStudent_Wishlist(int idStudent_Wishlist) {
		this.idStudent_Wishlist = idStudent_Wishlist;
	}

	public String getChangeRoom() {
		return this.changeRoom;
	}

	public void setChangeRoom(String changeRoom) {
		this.changeRoom = changeRoom;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPreferredLessons() {
		return this.preferredLessons;
	}

	public void setPreferredLessons(String preferredLessons) {
		this.preferredLessons = preferredLessons;
	}

	public String getPrefferedLessonTime() {
		return this.prefferedLessonTime;
	}

	public void setPrefferedLessonTime(String prefferedLessonTime) {
		this.prefferedLessonTime = prefferedLessonTime;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}