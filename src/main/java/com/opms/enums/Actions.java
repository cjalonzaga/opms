package com.opms.enums;

public enum Actions {
	SAVE("save"),
	UPDATE("update");
	
	private final String name;
	
	Actions(String name){
		this.name = name;
	}
	
	public String getName(){
        return  name;
    }
}
