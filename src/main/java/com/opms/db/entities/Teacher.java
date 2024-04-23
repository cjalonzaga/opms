package com.opms.db.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.enums.UserRoles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="teachers")
public class Teacher extends User{

	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	private List<Course> courses;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "imageId", referencedColumnName = "id")
	private Image image;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userDataId", referencedColumnName = "id")
    private UserData userData;
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	private List<Section> sections;
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	private List<Activity> activities;
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	private List<UserFile> files;
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	private List<Modules> modules;
	
	@JsonIgnore
	@OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
	private List<Subject> subjects;
	
	public Teacher(){
		super();
	}
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public UserData getUserData() {
		return userData;
	}

	public void setUserData(UserData userData) {
		this.userData = userData;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<Activity> getActivities() {
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}

	public List<UserFile> getFiles() {
		return files;
	}

	public void setFiles(List<UserFile> files) {
		this.files = files;
	}
	
	public List<Modules> getModules() {
		return modules;
	}

	public void setModules(List<Modules> modules) {
		this.modules = modules;
	}
	
	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

	@Transient
	public String getFullName() {
		return this.getFirstName() + " "+ this.getMiddleName() +" " + this.getLastName();
	}
}
