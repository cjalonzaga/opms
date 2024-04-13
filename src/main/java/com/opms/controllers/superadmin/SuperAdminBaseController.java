package com.opms.controllers.superadmin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.opms.db.dtos.SuperAdminDto;
import com.opms.services.SuperAdminService;

public class SuperAdminBaseController {
	@Autowired
	private SuperAdminService superAdminService;
	
	protected SuperAdminDto getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SuperAdminDto dto = superAdminService.findByUsername(authentication.getName());
		return dto;
	}
}
