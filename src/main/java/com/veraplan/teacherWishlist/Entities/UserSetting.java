package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user_setting database table.
 * 
 */
@Entity
@Table(name="user_setting")
@NamedQuery(name="UserSetting.findAll", query="SELECT u FROM UserSetting u")
public class UserSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int idUser_Settings;

	private byte option1;

	private byte option2;

	private byte option3;

	//bi-directional many-to-one association to User
	@OneToMany(mappedBy="userSetting")
	private List<User> users;

	public UserSetting() {
	}

	public int getIdUser_Settings() {
		return this.idUser_Settings;
	}

	public void setIdUser_Settings(int idUser_Settings) {
		this.idUser_Settings = idUser_Settings;
	}

	public byte getOption1() {
		return this.option1;
	}

	public void setOption1(byte option1) {
		this.option1 = option1;
	}

	public byte getOption2() {
		return this.option2;
	}

	public void setOption2(byte option2) {
		this.option2 = option2;
	}

	public byte getOption3() {
		return this.option3;
	}

	public void setOption3(byte option3) {
		this.option3 = option3;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public User addUser(User user) {
		getUsers().add(user);
		user.setUserSetting(this);

		return user;
	}

	public User removeUser(User user) {
		getUsers().remove(user);
		user.setUserSetting(null);

		return user;
	}

}