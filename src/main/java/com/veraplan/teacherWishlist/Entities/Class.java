package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the class database table.
 * 
 */
@Entity
@NamedQuery(name="Class.findAll", query="SELECT c FROM Class c")
public class Class implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idClass;

	private String name;

	//uni-directional many-to-one association to Lesson
	@ManyToOne
	@JoinColumn(name="lessonFK")
	private Lesson lesson;

	//bi-directional many-to-one association to Student
	@OneToMany(mappedBy="clazz")
	private List<Student> students;

	public Class() {
	}

	public int getIdClass() {
		return this.idClass;
	}

	public void setIdClass(int idClass) {
		this.idClass = idClass;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Lesson getLesson() {
		return this.lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}

	public List<Student> getStudents() {
		return this.students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public Student addStudent(Student student) {
		getStudents().add(student);
		student.setClazz(this);

		return student;
	}

	public Student removeStudent(Student student) {
		getStudents().remove(student);
		student.setClazz(null);

		return student;
	}

}