package com.opms.enums;

public enum TaskType {
	FILE_UPLOAD("Re-upload answered File"),
	SHARE_FILE("Share file via link");
	
	private String name;
	
	TaskType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
