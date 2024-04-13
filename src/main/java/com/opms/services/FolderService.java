package com.opms.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.opms.db.dtos.FolderDto;

public interface FolderService {
	FolderDto create(FolderDto dto , Long studentId);
	Page<FolderDto> filterSearch(String createdOn , String keyword , Pageable pageable);
	Page<FolderDto> findAllPageable(Pageable pageable);
}
