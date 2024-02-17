package com.opms.services;

import com.opms.db.dtos.StudentDto;

public interface StudentService {
	StudentDto findUser(String username);
	StudentDto create(StudentDto dto);
}
