package com.opms.db.entities;

import java.time.LocalDateTime;
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
@Table(name="assignments")
public class Assignment extends BaseEntity{
	
	@Basic
    @Column(
        name = "name",
        nullable = false,
        updatable = true
    )
	private String name;
	
	@Basic
    @Column(
        name = "instruction",
        nullable = false,
        updatable = true
    )
	private String instruction;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "subjectId",
            referencedColumnName = "id"
    )
	private Subject subject;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assignment", fetch = FetchType.LAZY)
	private List<UserFile> files;
	
	@Column(
		name = "dueDate",
		nullable = false,
        updatable = true
	)
	private LocalDateTime dueDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public Subject getSubject() {
		return subject;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public List<UserFile> getFiles() {
		return files;
	}

	public void setFiles(List<UserFile> files) {
		this.files = files;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}
	
}
