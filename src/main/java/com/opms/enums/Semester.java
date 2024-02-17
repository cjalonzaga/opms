package com.opms.enums;

public enum Semester {
	FIRST_SEM("First Semester"),
	SECOND_SEM("Second Semester");
	
	private String name;
	
	Semester(String name){
		this.name = name;
	}
	
	public String getName(){
        return  name;
    }
}
