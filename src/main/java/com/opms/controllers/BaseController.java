package com.opms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opms.db.dtos.TeacherDto;
import com.opms.db.dtos.UserDto;
import com.opms.db.entities.User;
import com.opms.enums.Actions;
import com.opms.services.TeacherService;

public class BaseController{
	
	@Autowired
	private TeacherService teacherService;
	
	protected static Actions action;
	
	protected TeacherDto getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		TeacherDto user = teacherService.findUser(authentication.getName());
        return user;
    }
	
}
