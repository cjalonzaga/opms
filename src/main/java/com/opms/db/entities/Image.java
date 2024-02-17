package com.opms.db.entities;

import com.opms.db.BaseEntity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="images")
public class Image extends BaseEntity{
	
	@Basic
    @Column(
        name = "fileName",
        nullable = false,
        updatable = true
    )
	private String fileName;
	
	@Basic
    @Column(
        name = "uri",
        nullable = false,
        updatable = true
    )
	private String uri;
	
	@Basic
    @Column(
        name = "originalFileName",
        nullable = false,
        updatable = true
    )
	private String originalFileName;
	
	@OneToOne(mappedBy = "image")
    private Teacher teacher;
	
	@OneToOne(mappedBy = "image")
    private Student student;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
	
}
