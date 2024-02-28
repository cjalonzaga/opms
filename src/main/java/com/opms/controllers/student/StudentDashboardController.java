package com.opms.controllers.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opms.db.dtos.StudentDto;
import com.opms.services.ActivityService;
import com.opms.services.SectionService;

@Controller
@RequestMapping("/student")
public class StudentDashboardController extends StudentBaseController{

	private final SectionService sectionService;
	private final ActivityService activityService;
	
	public StudentDashboardController(SectionService sectionService , ActivityService activityService){
		this.sectionService = sectionService;
		this.activityService = activityService;
	}
	
	@GetMapping("/dashboard")
	public String student(Model model) {
		
		StudentDto user = this.getCurrentUser();
		
		model.addAttribute("user", user );
		
		model.addAttribute("activities", activityService.findActivitiesByStudent( user.getId() ));
		
		return "student/dashboard";	
	}
	
}
