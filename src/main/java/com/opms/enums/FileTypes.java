package com.opms.enums;

public enum FileTypes {
	PDF("fa-solid fa-file-pdf"),
	WORD("fa-solid fa-file-word"),
	TEXT("fa-solid fa-file-lines"),
	EXCEL("fa-solid fa-file-excel"),
	PPT("fa-solid fa-file-powerpoint"),
	IMAGE("fa-solid fa-image");
	
	private String icon;
	
	FileTypes(String icon) {
		this.icon = icon;
	}
	
	public String getIcon() {
		return icon;
	}
}
