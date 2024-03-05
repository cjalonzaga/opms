package com.opms.db.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.opms.enums.TaskType;

public class ActivityDto {
	
	private Long id;
	
	private String title;
	
	private String instruction;
	
	private Long subjectId;
	
	private List<UserFileDto> files;
	
	private LocalDateTime createdOn;
	
	private TaskType taskType;
	
	private LocalDateTime dueDate;
	
	private String subject;
	
	private String createdBy;
	
	private List<Long> sectionIds;
	
	private List<SectionDto> sections;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Long> getSectionIds() {
		return sectionIds;
	}

	public void setSectionIds(List<Long> sectionIds) {
		this.sectionIds = sectionIds;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public List<SectionDto> getSections() {
		return sections;
	}

	public void setSections(List<SectionDto> sections) {
		this.sections = sections;
	}
}
