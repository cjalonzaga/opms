package com.opms.db.dtos;

import java.util.List;

import com.opms.db.entities.UserFile;
import com.opms.enums.AnswerStatus;

public class AnswerDto {
	
	private Long id;
	
	private String userId;
	
	private String activityId;
	
	private AnswerStatus status;
	
	private List<UserFile> files;
	
	private String fileUri;
	
	private String studentFullName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AnswerStatus getStatus() {
		return status;
	}

	public void setStatus(AnswerStatus status) {
		this.status = status;
	}

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public List<UserFile> getFiles() {
		return files;
	}

	public void setFiles(List<UserFile> files) {
		this.files = files;
	}

	public String getStudentFullName() {
		return studentFullName;
	}

	public void setStudentFullName(String studentFullName) {
		this.studentFullName = studentFullName;
	}
	
	
}
