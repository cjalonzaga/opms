package com.opms.enums;

public enum UserRoles {
	SUPER_ADMIN("Super Admin"),
	TEACHER("Teacher"),
	STUDENT("Student"),
	PARENT("Parent");
	
	private final String name;
	
	UserRoles(String name) {
		this.name = name;
	}
	
	public String getName(){
        return  name;
    }
}
