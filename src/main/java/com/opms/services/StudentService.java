package com.opms.services;

import java.util.List;

import com.opms.db.dtos.StudentDto;

public interface StudentService {
	StudentDto findUser(String username);
	StudentDto create(StudentDto dto);
	List<StudentDto> getAll();
	StudentDto getById(Long id);
}
