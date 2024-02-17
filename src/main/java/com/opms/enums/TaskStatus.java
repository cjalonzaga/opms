package com.opms.enums;

public enum TaskStatus {
	NEW("New" , "text-bg-info"),
	OVERDUE("Overdue" , "text-bg-warning"),
	FINISHED("Finished" , "text-bg-success"),
	FAILED_TO_SUBMIT("Failed To Submit" , "text-bg-danger");
	
	private String name;
	private String badge;
	
	TaskStatus(String name , String badge){
		this.name = name;
		this.badge = badge;
	}
	
	public String getName() {
		return name;
	}
	
	public String getBadge() {
		return badge;
	}
}
