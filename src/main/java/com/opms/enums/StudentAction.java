package com.opms.enums;

public enum StudentAction {
	FILE_UPLOAD("Re-upload Answered File"),
	SHARE_LINK("Share GDrive Link etc.");
	
	private String name;
	
	StudentAction(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
