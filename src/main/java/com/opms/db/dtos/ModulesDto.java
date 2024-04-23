package com.opms.db.dtos;

import java.util.List;

public class ModulesDto {
	private Long id;
	private String name;
	private String note;
	private List<ModuleFileDto> moduleFiles;
	private Long[] sectionId;
	private List<SectionDto> sections;
	private Long subjectId;
	
	private String subjectName;
	
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public List<ModuleFileDto> getModuleFiles() {
		return moduleFiles;
	}
	public void setModuleFiles(List<ModuleFileDto> moduleFiles) {
		this.moduleFiles = moduleFiles;
	}
	public Long[] getSectionId() {
		return sectionId;
	}
	public void setSectionId(Long[] sectionId) {
		this.sectionId = sectionId;
	}
	public List<SectionDto> getSections() {
		return sections;
	}
	public void setSections(List<SectionDto> sections) {
		this.sections = sections;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
}
