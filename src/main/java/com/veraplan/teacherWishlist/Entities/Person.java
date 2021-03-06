package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idPerson;

	private String address;

	@Temporal(TemporalType.DATE)
	private Date birthdate;

	private String city;

	private String firstName;

	private String gender;

	private String lastName;

	private int postalCode;

	//uni-directional many-to-one association to Staff
	@ManyToOne
	@JoinColumn(name="staffFK")
	private Staff staff;

	//uni-directional many-to-one association to Student
	@ManyToOne
	@JoinColumn(name="studentFK")
	private Student student;

	//uni-directional many-to-one association to Teacher
	@ManyToOne
	@JoinColumn(name="teacherFK")
	private Teacher teacher;

	//uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="userFK")
	private User user;

	public Person() {
	}

	public int getIdPerson() {
		return this.idPerson;
	}

	public void setIdPerson(int idPerson) {
		this.idPerson = idPerson;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getBirthdate() {
		return this.birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public Staff getStaff() {
		return this.staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return this.teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}