package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the student database table.
 * 
 */
@Entity
@NamedQuery(name="Student.findAll", query="SELECT s FROM Student s")
public class Student implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idStudent;

	@Temporal(TemporalType.DATE)
	private Date enrollmentDate;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="student")
	private List<Person> persons;

	//bi-directional many-to-many association to Class
	@ManyToMany
	@JoinTable(
		name="student_has_class"
		, joinColumns={
			@JoinColumn(name="Student_idStudent")
			}
		, inverseJoinColumns={
			@JoinColumn(name="Class_idClass")
			}
		)
	private List<Class> clazzs;

	//bi-directional many-to-one association to StudentWishlist
	@OneToMany(mappedBy="student")
	private List<StudentWishlist> studentWishlists;

	public Student() {
	}

	public int getIdStudent() {
		return this.idStudent;
	}

	public void setIdStudent(int idStudent) {
		this.idStudent = idStudent;
	}

	public Date getEnrollmentDate() {
		return this.enrollmentDate;
	}

	public void setEnrollmentDate(Date enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}

	public List<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}

	public Person addPerson(Person person) {
		getPersons().add(person);
		person.setStudent(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setStudent(null);

		return person;
	}

	public List<Class> getClazzs() {
		return this.clazzs;
	}

	public void setClazzs(List<Class> clazzs) {
		this.clazzs = clazzs;
	}

	public List<StudentWishlist> getStudentWishlists() {
		return this.studentWishlists;
	}

	public void setStudentWishlists(List<StudentWishlist> studentWishlists) {
		this.studentWishlists = studentWishlists;
	}

	public StudentWishlist addStudentWishlist(StudentWishlist studentWishlist) {
		getStudentWishlists().add(studentWishlist);
		studentWishlist.setStudent(this);

		return studentWishlist;
	}

	public StudentWishlist removeStudentWishlist(StudentWishlist studentWishlist) {
		getStudentWishlists().remove(studentWishlist);
		studentWishlist.setStudent(null);

		return studentWishlist;
	}

}