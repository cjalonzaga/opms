package com.opms.db.dtos;

import com.opms.enums.CouncilType;
import com.opms.enums.CourseLevel;
import com.opms.enums.UserRoles;

public class SuperAdminDto {
	
private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String middleName;
	
	private String address;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private UserDataDto userData;
	
	private String imageUri;
	
	private String fileName;
	
	private UserRoles userRole;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserDataDto getUserData() {
		return userData;
	}

	public void setUserData(UserDataDto userData) {
		this.userData = userData;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public UserRoles getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}
	
}
