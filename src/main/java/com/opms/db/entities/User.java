package com.opms.db.entities;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;
import com.opms.enums.CourseLevel;
import com.opms.enums.UserRoles;

import jakarta.persistence.*;


@MappedSuperclass
public class User{

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Basic
    @Column(
            name = "createdOn",
            nullable = false,
            updatable = true
    )
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@Basic
    @Column(
            name = "isValid",
            nullable = false,
            updatable = true,
            columnDefinition = "boolean default true"
    )
	private Boolean isValid = true;
	
	@Basic
    @Column(
            name = "updatedOn",
            nullable = false,
            updatable = true
    )
	private LocalDateTime updatedOn = LocalDateTime.now();

	@Basic
    @Column(
        name = "firstName",
        nullable = false,
        updatable = true
    )
	private String firstName;
	
	@Basic
    @Column(
        name = "lastName",
        nullable = false,
        updatable = true
    )
	private String lastName;
	
	@Basic
    @Column(
        name = "middleName",
        nullable = false,
        updatable = true
    )
	private String middleName;
	
	@Basic
    @Column(
        name = "address",
        nullable = false,
        updatable = true
    )
	private String address;
	
	@Basic
    @Column(
        name = "username",
        nullable = false,
        updatable = false
    )
	private String username;
	
	@Basic
    @Column(
        name = "email",
        nullable = false,
        updatable = false
    )
	private String email;
	
	@Basic
    @Column(
        name = "password",
        nullable = false,
        updatable = true
    )
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(
        name = "userRole",
        nullable = false,
        updatable = true
    )
	private UserRoles userRole;
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		if(getEmail() != null) {
			username = getEmail();
		}
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public UserRoles getUserRole() {
		return userRole;
	}

	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}
	
	@Transient
	public boolean hasRole(String roleName) {
		return this.getUserRole().getName() == roleName ? true : false ;
	}
}
