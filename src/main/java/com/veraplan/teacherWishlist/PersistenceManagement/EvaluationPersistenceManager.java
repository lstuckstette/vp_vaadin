package com.veraplan.teacherWishlist.PersistenceManagement;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.veraplan.teacherWishlist.Entities.Person;
import com.veraplan.teacherWishlist.Entities.Teacher;

public class EvaluationPersistenceManager {

	private EntityManager entityManager;
	private Teacher currentTeacher;

	public EvaluationPersistenceManager() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("mysqlconn");
		entityManager = emf.createEntityManager();
		
		setupDummyData();
	}

	public void setupDummyData() {
		
		// Create person if not exists
		Query personCheckQuery = entityManager
				.createQuery("SELECT p FROM Person p WHERE p.firstName = :firstName AND p.lastName = :lastName")
				.setParameter("firstName", "Max").setParameter("lastName", "Mustermann");
		List<Person> result =  personCheckQuery.getResultList();
		if (result.isEmpty()) {
			// create new Person and persist:
			System.out.println("dummy not found, creating one:");
			entityManager.getTransaction().begin();
			
			Person dummyPerson = new Person();
			//dummyPerson.setIdPerson(1);
			dummyPerson.setFirstName("Max");
			dummyPerson.setLastName("Mustermann");
			
			entityManager.persist(dummyPerson);
			
			entityManager.getTransaction().commit();
			System.out.println("persisted");
			//entityManager.close(); 
		} else {
			System.out.println("dummy found!");
		}

		// Create Teacher if not Exists

		//
	}

	public void setCurrentTeacher(Teacher t) {
		this.currentTeacher = t;
	}
	
	public Teacher getCurrentTeacher(){
		return this.currentTeacher;
	}

}
