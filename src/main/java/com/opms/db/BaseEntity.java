package com.opms.db;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class BaseEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Basic
    @Column(
            name = "createdOn",
            nullable = false,
            updatable = true
    )
	private LocalDateTime createdOn = LocalDateTime.now();
	
	@Basic
    @Column(
            name = "isValid",
            nullable = false,
            updatable = true,
            columnDefinition = "boolean default true"
    )
	private Boolean isValid = true;
	
	@Basic
    @Column(
            name = "updatedOn",
            nullable = false,
            updatable = true
    )
	private LocalDateTime updatedOn = LocalDateTime.now();
	
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	
	public Boolean getIsValid() {
		return isValid;
	}
	
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	
	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}
	
	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
}
