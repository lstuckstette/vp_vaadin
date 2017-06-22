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
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idTeacher;

	@Temporal(TemporalType.DATE)
	private Date hireDate;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="teacher")
	private List<Person> persons;

	//bi-directional many-to-one association to Lesson
	@ManyToOne
	private Lesson lesson;

	//bi-directional many-to-one association to TeacherWishlist
	@OneToMany(mappedBy="teacher")
	private List<TeacherWishlist> teacherWishlists;

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

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setTeacher(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setTeacher(null);

		return person;
	}

	public Lesson getLesson() {
		return this.lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public List<TeacherWishlist> getTeacherWishlists() {
		return this.teacherWishlists;
	}

	public void setTeacherWishlists(List<TeacherWishlist> teacherWishlists) {
		this.teacherWishlists = teacherWishlists;
	}

	public TeacherWishlist addTeacherWishlist(TeacherWishlist teacherWishlist) {
		getTeacherWishlists().add(teacherWishlist);
		teacherWishlist.setTeacher(this);

		return teacherWishlist;
	}

	public TeacherWishlist removeTeacherWishlist(TeacherWishlist teacherWishlist) {
		getTeacherWishlists().remove(teacherWishlist);
		teacherWishlist.setTeacher(null);

		return teacherWishlist;
	}

}