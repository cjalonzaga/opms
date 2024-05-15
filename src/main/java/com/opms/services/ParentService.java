package com.opms.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.ParentDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.TeacherDto;

public interface ParentService {
	ParentDto create(ParentDto dto , List<Long> studentIds);
	ParentDto findByUsername(String username);
	Page<ParentDto> filterSearch(String status , String createdOn , String keyword , Pageable pageable);
	Page<ParentDto> findAllPageable(Pageable pageable);
	ParentDto getById(Long id);
	ParentDto update(Long id , String status);
	
	ParentDto updateProfile(MultipartFile file , ParentDto parentDto);
	
	public ParentDto updateCredentials(ParentDto parentDto);
	
	String delete(Long id);
	boolean verifyPassword(String password, Long userId);
}
