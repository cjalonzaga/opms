package com.opms.db.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="folders")
public class Folder extends BaseEntity{
	
	@Basic
    @Column(
        name = "name",
        nullable = false,
        updatable = true
    )
	private String name;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "studentId",
            referencedColumnName = "id"
    )
	private Student student;
	
	@JsonIgnore
	@OneToMany(mappedBy = "folder", fetch = FetchType.LAZY)
	private List<UserFile> files;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public List<UserFile> getFiles() {
		return files;
	}

	public void setFiles(List<UserFile> files) {
		this.files = files;
	}
}
