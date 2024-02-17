package com.opms.db.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;
import com.opms.enums.UserRoles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="user_role")
public class UserRole extends BaseEntity{
	
//	@JsonIgnore
//	@ManyToMany(mappedBy = "userRole", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
//	private Set<User> user;
	
	@Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private UserRoles userRole;
	

//	public Set<User> getUser() {
//		return user;
//	}
//
//	public void setUser(Set<User> user) {
//		this.user = user;
//	}

	public UserRoles getRole() {
		return userRole;
	}

	public void setRole(UserRoles userRole) {
		this.userRole = userRole;
	}
	
}
