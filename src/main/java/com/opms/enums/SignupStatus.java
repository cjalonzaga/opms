package com.opms.enums;

public enum SignupStatus {
	NEW("New" , "badge text-bg-primary"),
	APPROVED("Approved" , "badge text-bg-success"),
	REJECTED("Rejected" , "badge text-bg-danger");
	
	private String name;
	private String badge;
	
	SignupStatus(String name , String badge){
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
