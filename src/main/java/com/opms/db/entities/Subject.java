package com.opms.db.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;
import com.opms.enums.CourseLevel;
import com.opms.enums.Semester;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="subjects")
public class Subject extends BaseEntity{
	
	@Basic
    @Column(
        name = "code",
        nullable = false,
        updatable = true
    )
	private String code;
	
	@Basic
    @Column(
        name = "description",
        nullable = false,
        updatable = true
    )
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(
        name = "semester",
        nullable = false,
        updatable = true
    )
	private Semester semester;
	
	@Enumerated(EnumType.STRING) 
	@Column(
        name = "courseLevel",
        nullable = false,
        updatable = true
    )
	private CourseLevel courseLevel;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "courseId",
            referencedColumnName = "id"
    )
	private Course course;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private List<Activity> activities;
	
	@JsonIgnore
	@OneToMany(mappedBy = "subject", fetch = FetchType.LAZY)
	private List<Assignment> assignment;
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public void setCourse(Course course) {
		this.course = course;
	}
	
	public Semester getSemester() {
		return semester;
	}
	
	public void setSemester(Semester semester) {
		this.semester = semester;
	}
	
	public CourseLevel getCourseLevel() {
		return courseLevel;
	}
	
	public void setCourseLevel(CourseLevel courseLevel) {
		this.courseLevel = courseLevel;
	}
	
	public List<Activity> getActivities() {
		return activities;
	}
	
	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public List<Assignment> getAssignment() {
		return assignment;
	}
	
	public void setAssignment(List<Assignment> assignment) {
		this.assignment = assignment;
	}
}
