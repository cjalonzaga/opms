package com.opms.db.dtos;

import java.time.LocalDateTime;
import java.util.List;

public class FolderDto {
	
	private Long id;
	private LocalDateTime createdOn;
	private StudentDto student;
	private List<UserFileDto> files;
	private String name;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	
	public StudentDto getStudent() {
		return student;
	}
	
	public void setStudent(StudentDto student) {
		this.student = student;
	}
	public List<UserFileDto> getFiles() {
		return files;
	}
	
	public void setFiles(List<UserFileDto> files) {
		this.files = files;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
