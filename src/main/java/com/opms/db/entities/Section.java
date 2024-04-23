package com.opms.db.entities;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;
import com.opms.enums.CourseLevel;

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
import jakarta.persistence.Table;

@Entity
@Table(name="section")
public class Section extends BaseEntity{
	
	@Basic
    @Column(
        name = "name",
        nullable = true,
        updatable = true
    )
	private String name;
	
	@Basic
	@Column(
            name = "unique_id",
            nullable = true,
            unique =  true,
            updatable = true
    )
	private String uniqueId;
	
	@Column(name = "courseLevel")
    @Enumerated(EnumType.STRING)
	private CourseLevel courseLevel;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "teacherId",
            referencedColumnName = "id"
    )
	private Teacher teacher;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "sections", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
	private List<Student> students;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "sections", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
	private List<Activity> activities;
	
	@JsonIgnore
	@JoinTable(
			name = "sectionCourse", 
			joinColumns = @JoinColumn(name = "sectionId"),
			inverseJoinColumns = @JoinColumn(name = "courseId")
		)    
	@ManyToMany(cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
	private List<Course> courses;
	
	@JsonIgnore
	@OneToMany(mappedBy = "section", fetch = FetchType.LAZY)
	private List<Answer> answers;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "sections", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
	private List<Modules> modules;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CourseLevel getCourseLevel() {
		return courseLevel;
	}

	public void setCourseLevel(CourseLevel courseLevel) {
		this.courseLevel = courseLevel;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}

	public List<Modules> getModules() {
		return modules;
	}

	public void setModules(List<Modules> modules) {
		this.modules = modules;
	}
}
