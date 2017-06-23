package com.veraplan.teacherWishlist.Model;

import com.veraplan.teacherWishlist.Entities.Person;
import com.veraplan.teacherWishlist.Entities.User;

public interface CurrentUser {

	public boolean isLoggedIn();
	public void setLoggedIn(boolean isLoggedIn);
	public User getUser();
	public void setUser(User user);
	public Person getPerson();
	
}
