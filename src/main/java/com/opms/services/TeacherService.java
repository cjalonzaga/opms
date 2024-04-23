package com.opms.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.TeacherDto;

public interface TeacherService {
	TeacherDto create(TeacherDto dto);
	TeacherDto findUser(String username);
	TeacherDto update( MultipartFile file , TeacherDto teacherDto);
	Boolean verifyPassword(String password, Long userId);
	TeacherDto updateCredentials(TeacherDto dto);
	TeacherDto getById(Long id);
	
	Page<TeacherDto> filterSearch(String status , String createdOn , String keyword , Pageable pageable);
	Page<TeacherDto> findAllPageable(Pageable pageable);
	TeacherDto update(Long id , String status);
	
	String delete(Long id);
}
