package com.opms.db.entities;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.enums.CouncilType;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name="students")
public class Student extends User{

	@JsonIgnore
	@JoinTable(
			name = "studentCourse", 
			joinColumns = @JoinColumn(name = "studentId"),
			inverseJoinColumns = @JoinColumn(name = "courseId")
		)    
	@ManyToMany(cascade = CascadeType.PERSIST , fetch = FetchType.EAGER)
	private List<Course> course;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "imageId", referencedColumnName = "id")
	private Image image;
	
	@JsonIgnore
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "userDataId", referencedColumnName = "id")
    private UserData userData;
	
	@JsonIgnore
	@JoinTable(
			name = "studentSection", 
			joinColumns = @JoinColumn(name = "studentId"),
			inverseJoinColumns = @JoinColumn(name = "sectionId")
		)    
	@ManyToMany(cascade = CascadeType.MERGE , fetch = FetchType.EAGER)
	private List<Section> sections;
	
	@Enumerated(EnumType.STRING)
	@Column(
        name = "councilType",
        nullable = false,
        updatable = true
    )
	private CouncilType councilType;
	
	@JsonIgnore
	@OneToMany(mappedBy = "student", fetch = FetchType.LAZY)
	private List<UserFile> files;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "students", cascade = CascadeType.MERGE , fetch = FetchType.LAZY)
	private Set<Parent> parent;

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

	public CouncilType getCouncilType() {
		return councilType;
	}

	public void setCouncilType(CouncilType councilType) {
		this.councilType = councilType;
	}

	public List<Course> getCourse() {
		return course;
	}

	public void setCourse(List<Course> course) {
		this.course = course;
	}

	public List<Section> getSections() {
		return sections;
	}

	public void setSections(List<Section> sections) {
		this.sections = sections;
	}

	public List<UserFile> getFiles() {
		return files;
	}

	public void setFiles(List<UserFile> files) {
		this.files = files;
	}

	public Set<Parent> getParent() {
		return parent;
	}

	public void setParent(Set<Parent> parent) {
		this.parent = parent;
	}
	
	@Transient
	public String getFullName() {
		return this.getFirstName() + " "+ this.getMiddleName() +" " + this.getLastName();
	}
}
