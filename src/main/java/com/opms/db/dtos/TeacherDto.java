package com.opms.db.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import com.opms.enums.SignupStatus;
import com.opms.enums.UserRoles;

public class TeacherDto implements Serializable{
	
	private static final long serialVersionUID = 3108679364014199668L;

	private Long id;
	
	private String firstName;
	
	private LocalDateTime createdOn;
	
	private String lastName;
	
	private String middleName;
	
	private String address;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private UserDataDto userData;
	
	private String imageUri;
	
	private String fileName;
	
	private Boolean isActivated;
	
	private SignupStatus status;
	
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

	public Boolean getIsActivated() {
		return isActivated;
	}

	public void setIsActivated(Boolean isActivated) {
		this.isActivated = isActivated;
	}

	public SignupStatus getStatus() {
		return status;
	}

	public void setStatus(SignupStatus status) {
		this.status = status;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public UserRoles getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}
}
