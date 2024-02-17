package com.opms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opms.db.dtos.TeacherDto;
import com.opms.db.dtos.UserDto;
import com.opms.services.TeacherService;

@Controller
@RequestMapping("/admin")
public class DashboardController extends BaseController{
	
	@Autowired
	private TeacherService teacherService;
	
	@GetMapping("/dashboard")
    public String home(Model model) throws JsonProcessingException {
		TeacherDto user = this.getCurrentUser();
        model.addAttribute("user" , user);
        return "dashboard";
	}
	
}
