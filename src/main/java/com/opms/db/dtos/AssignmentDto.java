package com.opms.db.dtos;

import java.util.List;

public class AssignmentDto {
	
	private Long id;
	
	private String name;
	
	private String instruction;
	
	private Long subjectId;
	
	private List<UserFileDto> files;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public List<UserFileDto> getFiles() {
		return files;
	}

	public void setFiles(List<UserFileDto> files) {
		this.files = files;
	}

}
