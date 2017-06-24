package com.veraplan.teacherWishlist.PersistenceManagement;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.vaadin.cdi.UIScoped;
import com.veraplan.teacherWishlist.Entities.Absence;
import com.veraplan.teacherWishlist.Entities.Periodicabsencetimeslot;
import com.veraplan.teacherWishlist.Entities.Person;
import com.veraplan.teacherWishlist.Entities.Role;
import com.veraplan.teacherWishlist.Entities.Teacher;
import com.veraplan.teacherWishlist.Entities.Teacherwishlist;
import com.veraplan.teacherWishlist.Entities.User;
import com.veraplan.teacherWishlist.Entities.UserSetting;
import com.veraplan.teacherWishlist.Model.RegistrationField;
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

		// check if dummy person exists
		TypedQuery<Person> personCheckQuery = entityManager
				.createQuery("SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName",
						Person.class)
				.setParameter("firstName", "Max").setParameter("lastName", "Mustermann");
		List<Person> result = personCheckQuery.getResultList();
		if (result.isEmpty()) {

			entityManager.getTransaction().begin();
			// create new User

			User dummyUser = new User();
			dummyUser.setIdUserEmail("test@test.com");
			dummyUser.setNotificationEmail("test2@test.com");
			dummyUser.setPassword("test");
			dummyUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
			dummyUser.setRole(getRole("admin"));
			UserSetting userSetting = new UserSetting();
			dummyUser.setUserSetting(userSetting);

			// create new Teacher
			Teacher dummyTeacher = new Teacher();
			dummyTeacher.setHireDate(new Timestamp(System.currentTimeMillis()));

			// create new Person
			Person dummyPerson = new Person();
			dummyPerson.setFirstName("Max");
			dummyPerson.setLastName("Mustermann");
			dummyPerson.setUser(dummyUser);
			// ArrayList<Person> tList = new ArrayList<>();
			// tList.add(dummyPerson);
			// dummyTeacher.setPersons(tList);
			dummyPerson.setTeacher(dummyTeacher);

			// persist above
			entityManager.persist(dummyUser.getRole());
			entityManager.persist(userSetting);
			entityManager.persist(dummyUser);
			entityManager.persist(dummyTeacher);
			entityManager.persist(dummyPerson);

			entityManager.getTransaction().commit();

		} else {
			System.out.println("dummy found!");
		}

	}

	public void persistEvaluation(User currentUser, ArrayList<TimeSlotRowContainer> inputPeriodicAbsence,
			ArrayList<VacationItem> inputVacation, String periodicAbsenceComment) {
		// TODO: add conversion methods to TimeSlotRowContainer + VacationItem

		// create new TeacherWishlist
		Teacherwishlist twl = new Teacherwishlist();
		twl.setPeriodicAbsenceComment(periodicAbsenceComment);
		twl.setDate(new Timestamp(System.currentTimeMillis()));
		// assign teacher to TeacherWishlist
		twl.setTeacher(this.getTeacher(currentUser));

		// map ArrayList<TimeSlotRowContainer> to single
		// PeriodicabsenceTimeslots and add to TeacherWishlist
		List<Periodicabsencetimeslot> periodicEntityList = TimeSlotRowContainer
				.toPeriodicabsencetimeslotList(inputPeriodicAbsence);
		for (Periodicabsencetimeslot pats : periodicEntityList) {
			pats.setTeacherwishlist(twl);
		}
		twl.setPeriodicabsencetimeslots(periodicEntityList);

		// map ArrayList<VacationItem> to single Absences and add to
		ArrayList<Absence> vacationEntityList = new ArrayList<>();
		for (VacationItem vi : inputVacation) {
			Absence absence = vi.toAbsenceEntity();
			absence.setTeacherwishlist(twl);
			vacationEntityList.add(absence);
		}
		twl.setAbsences(vacationEntityList);

		// persist all
		entityManager.getTransaction().begin();
		entityManager.persist(twl);
		for (Periodicabsencetimeslot pats : twl.getPeriodicabsencetimeslots()) {
			entityManager.persist(pats);
		}
		for (Absence absence : twl.getAbsences()) {
			entityManager.persist(absence);
		}
		entityManager.getTransaction().commit();

	}

	public boolean registerUser(Map<RegistrationField, String> enteredData) {
		// check for existing user with same PK(email)
		User checkExists = entityManager.find(User.class, enteredData.get(RegistrationField.EMAIL));
		if (checkExists != null) {
			return false;
		}
		// check for existing person with same firstname/lastname
		TypedQuery<Person> personCheckQuery = entityManager
				.createQuery("SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName",
						Person.class)
				.setParameter("firstName", enteredData.get(RegistrationField.FIRSTNAME))
				.setParameter("lastName", enteredData.get(RegistrationField.LASTNAME));
		if (!personCheckQuery.getResultList().isEmpty()) {
			return false;
		}
		// begin Transaction
		entityManager.getTransaction().begin();

		// Create User Using collected Data;
		User newUser = new User();
		newUser.setIdUserEmail(enteredData.get(RegistrationField.EMAIL));
		newUser.setNotificationEmail(enteredData.get(RegistrationField.NOTIFICATION_EMAIL));
		newUser.setPassword(enteredData.get(RegistrationField.PASSWORD));
		newUser.setCreateTime(new Timestamp(System.currentTimeMillis()));
		newUser.setRole(getRole("user"));
		UserSetting userSetting = new UserSetting();
		newUser.setUserSetting(userSetting);

		// Create Teacher using collected Data;
		Teacher newTeacher = new Teacher();
		DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		try {
			newTeacher.setHireDate(formatter.parse(enteredData.get(RegistrationField.HIREDATE)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// create Person using collected Data;
		Person newPerson = new Person();
		newPerson.setFirstName(enteredData.get(RegistrationField.FIRSTNAME));
		newPerson.setLastName(enteredData.get(RegistrationField.LASTNAME));
		try {
			newPerson.setBirthdate(formatter.parse(enteredData.get(RegistrationField.BIRTHDATE)));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		newPerson.setCity(enteredData.get(RegistrationField.CITY));
		newPerson.setAddress(enteredData.get(RegistrationField.ADDRESS));
		newPerson.setPostalCode(Integer.parseInt(enteredData.get(RegistrationField.POSTALCODE)));
		String genderString = enteredData.get(RegistrationField.GENDER);
		switch (genderString) {
		case "männlich":
			newPerson.setGender("M");
			break;
		case "weiblich":
			newPerson.setGender("F");
			break;
		default:
			newPerson.setGender("O");
		}

		newPerson.setUser(newUser);
		// ArrayList<Person> tList = new ArrayList<>();
		// tList.add(newPerson);
		// newTeacher.setPersons(tList);
		newPerson.setTeacher(newTeacher);

		// persist generated Entities and commit

		entityManager.persist(newUser.getRole());
		entityManager.persist(userSetting);
		entityManager.persist(newUser);
		entityManager.persist(newTeacher);
		entityManager.persist(newPerson);

		entityManager.getTransaction().commit();

		return true;
	}

	public boolean checkUserLogin(String email, String password) {

		User user = entityManager.find(User.class, email);
		if (user != null) {
			if (user.getPassword().equals(password)) {
				return true;
			}
			return false;
		}

		return false;
	}

	public User getSingleUser(String emailId) {
		return entityManager.getReference(User.class, emailId);
	}

	public Role getRole(String roleText) {
		// check exists
		TypedQuery<Role> roleQuery = entityManager.createQuery("SELECT r FROM Role r WHERE r.role = :role", Role.class)
				.setParameter("role", roleText);
		List<Role> result = roleQuery.getResultList();
		if (result.isEmpty()) {
			// create new Role, persist and return
			Role newRole = new Role();
			newRole.setRole(roleText);
			return newRole;
		} else {
			// return existing role
			return result.get(0);
		}
	}

	public Teacher getTeacher(User currentUser) {

		return getPerson(currentUser).getTeacher();
	}

	public Person getPerson(User currentUser) {
		TypedQuery<Person> getTeacherFromUser = entityManager
				.createQuery("SELECT p FROM Person p WHERE p.user.idUserEmail = :userId", Person.class)
				.setParameter("userId", currentUser.getIdUserEmail());
		List<Person> resultList = getTeacherFromUser.getResultList();
		if (resultList.isEmpty()) {
			return null;
		}
		return resultList.get(0);
	}

	public List<Person> getPeople() {
		TypedQuery<Person> personCheckQuery = entityManager.createQuery("SELECT p FROM Person p", Person.class);
		return personCheckQuery.getResultList();
	}

	public List<User> getUser() {
		TypedQuery<User> personCheckQuery = entityManager.createQuery("SELECT u FROM User u", User.class);
		return personCheckQuery.getResultList();
	}

	public List<Teacher> getTeachers() {
		TypedQuery<Teacher> personCheckQuery = entityManager.createQuery("SELECT t FROM Teacher t", Teacher.class);
		return personCheckQuery.getResultList();
	}

}
