package com.opms.db.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="modules")
public class Modules extends BaseEntity{
	
	@Basic
    @Column(
        name = "name",
        nullable = true,
        updatable = true
    )
	private String name;
	
	@Basic
    @Column(
        name = "note",
        nullable = true,
        updatable = true,
        length=9999
    )
	private String note;
	
	@JsonIgnore
	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
	List<ModuleFile> moduleFiles;
	
	@JsonIgnore
	@JoinTable(
			name = "moduleSection", 
			joinColumns = @JoinColumn(name = "moduleId"),
			inverseJoinColumns = @JoinColumn(name = "sectionId")
		)    
	@ManyToMany(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
	private List<Section> sections;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "teacherId",
            referencedColumnName = "id"
    )
	private Teacher teacher;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "subjectId",
            referencedColumnName = "id"
    )
    private Subject subject;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ModuleFile> getModuleFiles() {
		return moduleFiles;
	}

	public void setModuleFiles(List<ModuleFile> moduleFiles) {
		this.moduleFiles = moduleFiles;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}
}
