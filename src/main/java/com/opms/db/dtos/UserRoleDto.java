package com.opms.db.dtos;

import java.util.Set;

import com.opms.enums.UserRoles;

public class UserRoleDto {
	
	private Long id;
	private Set<UserDto> user;
	private UserRoles userRole;
	
	public Set<UserDto> getUser() {
		return user;
	}
	public void setUser(Set<UserDto> user) {
		this.user = user;
	}
	
	public UserRoles getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRoles userRole) {
		this.userRole = userRole;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
