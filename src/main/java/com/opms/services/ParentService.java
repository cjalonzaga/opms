package com.opms.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.opms.db.dtos.ParentDto;
import com.opms.db.dtos.StudentDto;

public interface ParentService {
	ParentDto create(ParentDto dto , List<Long> studentIds);
	ParentDto findByUsername(String username);
	Page<ParentDto> filterSearch(String status , String createdOn , String keyword , Pageable pageable);
	Page<ParentDto> findAllPageable(Pageable pageable);
	ParentDto getById(Long id);
	ParentDto update(Long id , String status);
	
	String delete(Long id);
}
