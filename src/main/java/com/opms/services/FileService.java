package com.opms.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.UserFileDto;
import com.opms.db.entities.UserFile;

public interface FileService extends FileAware<UserFileDto , UserFile>{
	UserFileDto upload(UserFileDto dto , Long userId , MultipartFile file);
	Page<UserFileDto> filterSearch(String status , String createdOn , String keyword , Pageable pageable);
	Page<UserFileDto> findAllPageable(Pageable pageable);
	Page<UserFileDto> findAllPageable(Pageable pageable , Long folderId);
	void delete(Long fileId);
}
