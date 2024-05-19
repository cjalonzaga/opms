package com.opms.enums;

public enum AnswerStatus {
	SUBMITTED("Submitted" ,"badge text-bg-primary"),
	CHECKED("Checked" , "badge text-bg-success"),
	LATE("Late" , "badge text-bg-danger");
	
	private String name;
	private String badge;
	
	AnswerStatus(String name , String badge){
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
