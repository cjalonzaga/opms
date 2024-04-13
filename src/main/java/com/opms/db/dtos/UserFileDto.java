package com.opms.db.dtos;

import java.time.LocalDateTime;

import com.opms.enums.FileTypes;

public class UserFileDto {
	
	private Long id;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private String fileName;
	private String uri;
	private String folderName;
	private Long folderId;
	private FileTypes type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public Long getFolderId() {
		return folderId;
	}
	public void setFolderId(Long folderId) {
		this.folderId = folderId;
	}
	public FileTypes getType() {
		return type;
	}
	public void setType(FileTypes type) {
		this.type = type;
	}
	
}
