package com.opms.enums;

public enum CouncilType {
	TTED("Technical Teacher Education"),
	BSED("Bachelor of Secondary Education"),
	BEED("Bachelor Elementary Education");
	
	private String name;
	
	CouncilType(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
}
