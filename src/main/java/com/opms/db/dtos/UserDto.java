package com.opms.db.dtos;

import java.io.Serializable;
import java.util.Set;

import com.opms.enums.CourseLevel;

public class UserDto implements Serializable{
	
	private static final long serialVersionUID = -8813575060319321761L;
	
	private Long id;
	private String firstName;
	private String lastName;
	private String middleName;
	private String address;
	private String username;
	private String password;
	private Long roleId; // for adding role from forms
	private Set<UserRoleDto> userRole;
	private String email;
	private UserDataDto userData;
	private String imageUri;
	private String fileName;
	private Long courseId;
	private CourseLevel courseLevel;
	
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
	
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Set<UserRoleDto> getUserRole() {
		return userRole;
	}
	public void setUserRole(Set<UserRoleDto> userRole) {
		this.userRole = userRole;
	}
	public UserDataDto getUserData() {
		return userData;
	}
	public void setUserData(UserDataDto userData) {
		this.userData = userData;
	}
	public CourseLevel getCourseLevel() {
		return courseLevel;
	}
	public void setCourseLevel(CourseLevel courseLevel) {
		this.courseLevel = courseLevel;
	}
	public Long getCourseId() {
		return courseId;
	}
	public void setCourseId(Long courseId) {
		this.courseId = courseId;
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
}
