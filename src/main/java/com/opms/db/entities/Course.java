package com.opms.db.entities;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;
import com.opms.enums.CouncilType;

import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="courses")
public class Course extends BaseEntity{
	
	@Basic
    @Column(
        name = "name",
        nullable = false,
        updatable = true
    )
	private String name;
	
	@Basic
    @Column(
        name = "description",
        nullable = true,
        updatable = true
    )
	private String description;
	
	@JsonIgnore
	@OneToMany(mappedBy = "course", fetch = FetchType.LAZY)
	private List<Subject> subjects;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "teacherId",
            referencedColumnName = "id"
    )
	private Teacher teacher;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "course", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
	private Set<Student> student;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "courses", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
	private List<Section> sections;
	
	@Basic
    @Column(
        name = "abbreviation",
        nullable = false,
        updatable = true
    )
	private String abbreviation;
	
	@Enumerated(EnumType.STRING)
	@Column(
        name = "councilType",
        nullable = false,
        updatable = true
    )
	private CouncilType councilType;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Subject> getSubjects() {
		return subjects;
	}
	
	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
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
	
	public Set<Student> getStudent() {
		return student;
	}
	
	public void setStudent(Set<Student> student) {
		this.student = student;
	}

	public Teacher getTeacher() {
		return teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public CouncilType getCouncilType() {
		return councilType;
	}
	public void setCouncilType(CouncilType councilType) {
		this.councilType = councilType;
	}
	public List<Section> getSections() {
		return sections;
	}
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	
}
