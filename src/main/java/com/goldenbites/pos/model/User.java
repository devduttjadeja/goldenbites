package com.goldenbites.pos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "user")
public class User {

	@Id
	private String userId;

	@Field(name = "user_name")
	private String userName;

	@Field(name = "user_password")
	private String userPassword;

	@Field(name = "user_email")
	private String userEmail;
	
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Field(name = "user_role")
	private String userRole;

	private int active;

	public User(String userName, String userPassword, String userEmail, String userRole) {
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userRole = userRole;
		this.active = 1;
	}

	public User() {

	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userRole="
				+ userRole + "]";
	}

}
