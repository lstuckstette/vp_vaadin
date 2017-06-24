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
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idStudent;

	@Temporal(TemporalType.DATE)
	private Date enrollmentDate;

	//bi-directional many-to-one association to Class
	@ManyToOne
	@JoinColumn(name="classFK")
	private Class clazz;

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

	public Class getClazz() {
		return this.clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
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