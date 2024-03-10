package com.opms.enums;

public enum AnswerStatus {
	SUBMITTED("Submitted"),
	CHECKED("Checked");
	
	private String name;
	
	AnswerStatus(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
