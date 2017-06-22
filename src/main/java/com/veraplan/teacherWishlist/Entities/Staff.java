package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the staff database table.
 * 
 */
@Entity
@NamedQuery(name="Staff.findAll", query="SELECT s FROM Staff s")
public class Staff implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int idStaff;

	@Temporal(TemporalType.DATE)
	private Date hireDate;

	//bi-directional many-to-one association to Person
	@OneToMany(mappedBy="staff")
	private List<Person> persons;

	public Staff() {
	}

	public int getIdStaff() {
		return this.idStaff;
	}

	public void setIdStaff(int idStaff) {
		this.idStaff = idStaff;
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
		person.setStaff(this);

		return person;
	}

	public Person removePerson(Person person) {
		getPersons().remove(person);
		person.setStaff(null);

		return person;
	}

}