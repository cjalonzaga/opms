package com.opms.enums;

public enum CourseLevel {
	FIRST_YEAR("First Year College"),
	SECOND_YEAR("Second Year College"),
	THIRD_YEAR("Third Year College"),
	FOURTH_YEAR("Fourth Year College");
	
	private final String name;
	
	CourseLevel(String name){
		this.name = name;
	}
	
	public String getName(){
        return  name;
    }
}
