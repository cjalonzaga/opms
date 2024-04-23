package com.opms.db.dtos;

import com.opms.enums.FileTypes;

public class ModuleFileDto {
	
	private Long id;
	private String fileName;
	private String originalFileName;
	private String uri;
	private FileTypes type;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getOriginalFileName() {
		return originalFileName;
	}
	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public FileTypes getType() {
		return type;
	}
	public void setType(FileTypes type) {
		this.type = type;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
}
