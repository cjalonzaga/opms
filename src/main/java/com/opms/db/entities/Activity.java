package com.opms.db.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;
import com.opms.enums.TaskType;

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
@Table(name="activities")
public class Activity extends BaseEntity{
	
	@Basic
    @Column(
        name = "title",
        nullable = false,
        updatable = true
    )
	private String title;
	
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
	@OneToMany(mappedBy = "activity", fetch = FetchType.LAZY)
	private List<UserFile> files;
	
	@Column(
		name = "dueDate",
		nullable = false,
        updatable = true
	)
	private LocalDateTime dueDate;
	
	@Column(name = "taskType")
    @Enumerated(EnumType.STRING)
	private TaskType taskType;
	

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
	}

	public LocalDateTime getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDateTime dueDate) {
		this.dueDate = dueDate;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	
}
