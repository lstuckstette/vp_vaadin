package com.veraplan.teacherWishlist.Model;

import com.veraplan.teacherWishlist.Entities.Person;
import com.veraplan.teacherWishlist.Entities.User;

/**
 * CurrentUser defines a interface for providing  information about the current user of the application
 * @author Lukas Stuckstette
 */
public interface CurrentUser {

	/**
	 * @return returns true if the user is loggen in, otherwise false
	 */
	public boolean isLoggedIn();
	
	/**
	 * sets the current users 'logged-in' flag
	 * @param isLoggedIn true if logged in, false otherwise
	 */
	public void setLoggedIn(boolean isLoggedIn);
	
	/**
	 * @return returns the user entity associated with the current user
	 */
	public User getUser();
	
	/**
	 * sets the user entity of the current user
	 * @param user user
	 */
	public void setUser(User user);
	
	/**
	 * sets the person entity of the current user
	 * @param p person
	 */
	public void setPerson(Person p);
	
	/**
	 * @return returns the person entity associated with the current user
	 */
	public Person getPerson();
}
