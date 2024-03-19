package com.opms.controllers.parent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opms.db.dtos.ParentDto;
import com.opms.services.ParentService;

public class ParentBaseController {
	@Autowired
	private ParentService parentService;
	
	protected ParentDto getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ParentDto user = parentService.findByUsername(authentication.getName());
		
		return user;
	}
}
