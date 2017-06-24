package com.veraplan.teacherWishlist.Entities;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idUserEmail;

	@Column(name="create_time")
	private Timestamp createTime;

	private String notificationEmail;

	private String password;

	//bi-directional many-to-one association to Role
	@ManyToOne
	@JoinColumn(name="roleFK")
	private Role role;

	//bi-directional many-to-one association to UserSetting
	@ManyToOne
	@JoinColumn(name="usersettingFK")
	private UserSetting userSetting;

	public User() {
	}

	public String getIdUserEmail() {
		return this.idUserEmail;
	}

	public void setIdUserEmail(String idUserEmail) {
		this.idUserEmail = idUserEmail;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getNotificationEmail() {
		return this.notificationEmail;
	}

	public void setNotificationEmail(String notificationEmail) {
		this.notificationEmail = notificationEmail;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return this.role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public UserSetting getUserSetting() {
		return this.userSetting;
	}

	public void setUserSetting(UserSetting userSetting) {
		this.userSetting = userSetting;
	}

}