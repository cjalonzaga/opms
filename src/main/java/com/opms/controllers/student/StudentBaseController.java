package com.opms.controllers.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opms.db.dtos.StudentDto;
import com.opms.services.StudentService;

public class StudentBaseController {
	
	@Autowired
	private StudentService studentService;
	
	protected StudentDto getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		StudentDto user = studentService.findUser(authentication.getName());
		return user;
	}
}
