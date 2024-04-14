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
@Table(name="modules")
public class Module extends BaseEntity{
	
	@Basic
    @Column(
        name = "name",
        nullable = true,
        updatable = true
    )
	private String name;
	
	@JsonIgnore
	@OneToMany(mappedBy = "module", fetch = FetchType.LAZY)
	List<ModuleFile> moduleFiles;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "sectionId",
            referencedColumnName = "id"
    )
	private Section section;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ModuleFile> getModuleFiles() {
		return moduleFiles;
	}

	public void setModuleFiles(List<ModuleFile> moduleFiles) {
		this.moduleFiles = moduleFiles;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}
}
