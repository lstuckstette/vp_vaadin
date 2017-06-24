package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the teacher database table.
 * 
 */
@Entity
@NamedQuery(name="Teacher.findAll", query="SELECT t FROM Teacher t")
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idTeacher;

	@Temporal(TemporalType.DATE)
	private Date hireDate;

	//bi-directional many-to-one association to Lesson
	@ManyToOne
	@JoinColumn(name="lessonFK")
	private Lesson lesson;

	//bi-directional many-to-one association to Teacherwishlist
	@OneToMany(mappedBy="teacher")
	private List<Teacherwishlist> teacherwishlists;

	public Teacher() {
	}

	public int getIdTeacher() {
		return this.idTeacher;
	}

	public void setIdTeacher(int idTeacher) {
		this.idTeacher = idTeacher;
	}

	public Date getHireDate() {
		return this.hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Lesson getLesson() {
		return this.lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public List<Teacherwishlist> getTeacherwishlists() {
		return this.teacherwishlists;
	}

	public void setTeacherwishlists(List<Teacherwishlist> teacherwishlists) {
		this.teacherwishlists = teacherwishlists;
	}

	public Teacherwishlist addTeacherwishlist(Teacherwishlist teacherwishlist) {
		getTeacherwishlists().add(teacherwishlist);
		teacherwishlist.setTeacher(this);

		return teacherwishlist;
	}

	public Teacherwishlist removeTeacherwishlist(Teacherwishlist teacherwishlist) {
		getTeacherwishlists().remove(teacherwishlist);
		teacherwishlist.setTeacher(null);

		return teacherwishlist;
	}

}