package com.opms.enums;

public enum UserRoles {
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
