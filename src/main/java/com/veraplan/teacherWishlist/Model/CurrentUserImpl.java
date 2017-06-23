package com.veraplan.teacherWishlist.Model;

import java.io.Serializable;

import com.vaadin.cdi.UIScoped;
import com.veraplan.teacherWishlist.Entities.Person;
import com.veraplan.teacherWishlist.Entities.User;

@SuppressWarnings("serial")
@UIScoped
public class CurrentUserImpl implements CurrentUser, Serializable{

	private boolean isLoggedIn;
	private User user;


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

	public Person getPerson() {
		return user.getPersons().get(0);
	}


	
	

}
