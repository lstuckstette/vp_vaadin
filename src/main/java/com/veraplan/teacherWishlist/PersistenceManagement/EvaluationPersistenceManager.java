package com.veraplan.teacherWishlist.PersistenceManagement;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.vaadin.cdi.UIScoped;
import com.veraplan.teacherWishlist.Entities.Person;
import com.veraplan.teacherWishlist.Entities.Role;
import com.veraplan.teacherWishlist.Entities.Teacher;
import com.veraplan.teacherWishlist.Entities.User;
import com.veraplan.teacherWishlist.Entities.UserSetting;
import com.veraplan.teacherWishlist.Model.TimeSlotRowContainer;
import com.veraplan.teacherWishlist.Model.VacationItem;

@UIScoped
public class EvaluationPersistenceManager {

	private EntityManager entityManager;

	public EvaluationPersistenceManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlconn");
		entityManager = emf.createEntityManager();

		setupDummyData();
	}

	public void setupDummyData() {
		int dummyPersonID;
		// check if dummy person exists
		Query personCheckQuery = entityManager
				.createQuery("SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName")
				.setParameter("firstName", "Max").setParameter("lastName", "Mustermann");
		List<Person> result = personCheckQuery.getResultList();
		if (result.isEmpty()) {

			entityManager.getTransaction().begin();
			// create new User

			User dummyUser = new User();
			dummyUser.setIdUserEmail("test@test.com");
			dummyUser.setPassword("test");
			dummyUser.setCreateTime(new Timestamp(1));
			Role role = new Role();
			role.setRole("admin");
			dummyUser.setRole(role);
			dummyUser.setUserSetting(new UserSetting());

			// create new Teacher
			Teacher dummyTeacher = new Teacher();
			dummyTeacher.setHireDate(new Date(1));

			// create new Person
			Person dummyPerson = new Person();
			dummyPerson.setFirstName("Max");
			dummyPerson.setLastName("Mustermann");
			dummyPerson.setUser(dummyUser);
			dummyTeacher.getPersons().add(dummyPerson);
			dummyPerson.setTeacher(dummyTeacher);

			// persist above
			entityManager.persist(dummyUser);
			entityManager.persist(dummyTeacher);
			entityManager.persist(dummyPerson);

			entityManager.getTransaction().commit();

			dummyPersonID = dummyPerson.getIdPerson();

		} else {
			System.out.println("dummy found!");
		}

	}



	public void persistEvaluation(Teacher currentTeacher, ArrayList<TimeSlotRowContainer> periodicAbsence,
			ArrayList<VacationItem> absence) {
		// create new TeacherWishlist

		// assign teacher to TeacherWishlist

		// map ArrayList<TimeSlotRowContainer> to single
		// PeriodicabsenceTimeslots and add to TeacherWishlist

		// map ArrayList<VacationItem> to single Absences and add to
		// TeacherWishlist

		// persist all
	}

	public void registerUser() {
		// check for existing user with same PK(email)

		// check for existing person with same firstname/lastname

		// Create Teacher using collected Data;

		// Create User Using collected Data;

		// create Person using collected Data;

		// add Teacher,User to Person;
	}
	
	public List<Person> getPeople() {
		Query personCheckQuery = entityManager.createQuery("SELECT p FROM Person p");
		return personCheckQuery.getResultList();
	}

	public List<User> getUser() {
		Query personCheckQuery = entityManager.createQuery("SELECT u FROM User u");
		return personCheckQuery.getResultList();
	}

	public List<Teacher> getTeachers() {
		Query personCheckQuery = entityManager.createQuery("SELECT t FROM Teacher t");
		return personCheckQuery.getResultList();
	}

}
