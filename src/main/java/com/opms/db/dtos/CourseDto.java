package com.opms.db.dtos;

import java.time.LocalDateTime;
import java.util.List;

import com.opms.enums.CouncilType;

public class CourseDto {
	
	private Long id;
	private String name;
	private LocalDateTime createdOn;
	private String description;
	private List<SubjectDto> subjects;
	private String abbreviation;
	private Long[] sectionId;
	private CouncilType councilType;
	private Long teacherId;
	private String createdBy;
	private List<SectionDto> sections;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<SubjectDto> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<SubjectDto> subjects) {
		this.subjects = subjects;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAbbreviation() {
		return abbreviation;
	}
	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}
	
	public CouncilType getCouncilType() {
		return councilType;
	}
	public void setCouncilType(CouncilType councilType) {
		this.councilType = councilType;
	}
	public Long getTeacherId() {
		return teacherId;
	}
	public void setTeacherId(Long teacherId) {
		this.teacherId = teacherId;
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
	public Long[] getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long[] sectionId) {
		this.sectionId = sectionId;
	}
	
}
