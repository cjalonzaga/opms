package com.opms.services;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.UserFileDto;
import com.opms.db.entities.UserFile;

import jakarta.servlet.http.HttpServletResponse;

public interface FileService extends FileAware<UserFileDto , UserFile>{
	UserFileDto upload(UserFileDto dto , Long userId , MultipartFile file);
	Page<UserFileDto> filterSearch(String status , String createdOn , String keyword , Pageable pageable);
	Page<UserFileDto> findAllPageableByUser(Pageable pageable , Long userId);
	Page<UserFileDto> findAllPageable(Pageable pageable , Long folderId);
	void delete(Long fileId);
	
	UserFileDto uploadLocal(UserFileDto dto , MultipartFile file);
	
	void dowloadFolderFiles(Long folderId, HttpServletResponse response) throws IOException;
}
