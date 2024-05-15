package com.opms.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opms.db.dtos.TeacherDto;
import com.opms.services.ActivityService;
import com.opms.services.SectionService;
import com.opms.services.TeacherService;

@Controller
@RequestMapping("/admin")
public class DashboardController extends BaseController{
	
	
	private final TeacherService teacherService;
	private final SectionService sectionService;
	private final ActivityService activityService;
	
	DashboardController(TeacherService teacherService , SectionService sectionService , ActivityService activityService){
		this.teacherService = teacherService;
		this.sectionService = sectionService;
		this.activityService = activityService;
	}
	
	@GetMapping("/dashboard")
    public String home(Model model) throws JsonProcessingException {
		TeacherDto user = this.getCurrentUser();
        model.addAttribute("user" , user);
        
        model.addAttribute("sections", sectionService.getAll());
        
        model.addAttribute("summary", activityService.getSummary(user.getId()));
        
        return "dashboard";
	}
	
}
