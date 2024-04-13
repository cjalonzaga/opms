package com.opms.db.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.opms.db.BaseEntity;
import com.opms.enums.FileTypes;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="user_files")
public class UserFile extends BaseEntity{
	
	@Basic
    @Column(
        name = "fileName",
        nullable = false,
        updatable = true
    )
	private String fileName;
	
	@Basic
    @Column(
        name = "originalFileName",
        nullable = false,
        updatable = true
    )
	private String originalFileName;
	
	@Basic
    @Column(
        name = "uri",
        nullable = false,
        updatable = true
    )
	private String uri;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "activityId",
            referencedColumnName = "id"
    )
	private Activity activity;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "assignmentId",
            referencedColumnName = "id"
    )
	private Assignment assignment;
	
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "studentId",
            referencedColumnName = "id"
    )
	private Student student;
	
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
            name = "answerId",
            referencedColumnName = "id"
    )
	private Answer answer;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "folderId",
            referencedColumnName = "id"
    )
	private Folder folder;
	
	@Enumerated(EnumType.STRING)
    @Column(
        name = "fileType",
        nullable = false,
        updatable = true
    )
	private FileTypes type;
	
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

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public Assignment getAssignment() {
		return assignment;
	}

	public void setAssignment(Assignment assignment) {
		this.assignment = assignment;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public Answer getAnswer() {
		return answer;
	}

	public void setAnswer(Answer answer) {
		this.answer = answer;
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

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	public FileTypes getType() {
		return type;
	}

	public void setType(FileTypes type) {
		this.type = type;
	}
}
