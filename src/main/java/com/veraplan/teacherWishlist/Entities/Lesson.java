package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lesson database table.
 * 
 */
@Entity
@NamedQuery(name="Lesson.findAll", query="SELECT l FROM Lesson l")
public class Lesson implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idClass;

	//uni-directional many-to-one association to Event
	@ManyToOne
	@JoinColumn(name="eventFK")
	private Event event;

	//uni-directional many-to-one association to Subject
	@ManyToOne
	@JoinColumn(name="subjectFK")
	private Subject subject;

	//bi-directional many-to-one association to Teacher
	@OneToMany(mappedBy="lesson")
	private List<Teacher> teachers;

	public Lesson() {
	}

	public int getIdClass() {
		return this.idClass;
	}

	public void setIdClass(int idClass) {
		this.idClass = idClass;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Subject getSubject() {
		return this.subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<Teacher> getTeachers() {
		return this.teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public Teacher addTeacher(Teacher teacher) {
		getTeachers().add(teacher);
		teacher.setLesson(this);

		return teacher;
	}

	public Teacher removeTeacher(Teacher teacher) {
		getTeachers().remove(teacher);
		teacher.setLesson(null);

		return teacher;
	}

}