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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idClass;

	private int class_Teachers_idClass_Teachers;

	//bi-directional many-to-one association to Class
	@ManyToOne
	@JoinColumn(name="Class_idClass")
	private Class clazz;

	//bi-directional many-to-one association to Event
	@ManyToOne
	private Event event;

	//bi-directional many-to-one association to Room
	@ManyToOne
	private Room room;

	//bi-directional many-to-one association to Subject
	@ManyToOne
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

	public int getClass_Teachers_idClass_Teachers() {
		return this.class_Teachers_idClass_Teachers;
	}

	public void setClass_Teachers_idClass_Teachers(int class_Teachers_idClass_Teachers) {
		this.class_Teachers_idClass_Teachers = class_Teachers_idClass_Teachers;
	}

	public Class getClazz() {
		return this.clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Event getEvent() {
		return this.event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Room getRoom() {
		return this.room;
	}

	public void setRoom(Room room) {
		this.room = room;
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