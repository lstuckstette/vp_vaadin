package com.veraplan.teacherWishlist.Model;

import java.io.Serializable;

import com.vaadin.cdi.UIScoped;
import com.veraplan.teacherWishlist.Entities.Person;
import com.veraplan.teacherWishlist.Entities.User;

/**
 * CurrentUserImpl implements the CurrentUser interface and contains information about the current user of the application
 * @author Lukas Stuckstette
 */
@SuppressWarnings("serial")
@UIScoped
public class CurrentUserImpl implements CurrentUser, Serializable {

	private boolean isLoggedIn;
	private User user;
	private Person person;

	public CurrentUserImpl() {
		this.isLoggedIn = false;
	}

	public boolean isLoggedIn() {
		return isLoggedIn;
	}

	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setPerson(Person person) {
		this.person = person;

	}

	public Person getPerson() {
		return person;
	}

}
