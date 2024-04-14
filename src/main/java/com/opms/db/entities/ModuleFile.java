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
@Table(name="module_files")
public class ModuleFile extends BaseEntity{
	
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
	
	@Enumerated(EnumType.STRING)
    @Column(
        name = "fileType",
        nullable = false,
        updatable = true
    )
	private FileTypes type;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "moduleId",
            referencedColumnName = "id"
    )
	private Module module;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public FileTypes getType() {
		return type;
	}

	public void setType(FileTypes type) {
		this.type = type;
	}

	public Module getModule() {
		return module;
	}

	public void setModule(Module module) {
		this.module = module;
	}
}
