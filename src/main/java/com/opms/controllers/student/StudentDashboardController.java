package com.opms.controllers.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opms.services.SectionService;

@Controller
@RequestMapping("/student")
public class StudentDashboardController extends StudentBaseController{

	private final SectionService sectionService;
	
	public StudentDashboardController(SectionService sectionService){
		this.sectionService = sectionService;
	}
	
	@GetMapping("/dashboard")
	public String student(Model model) {
		
		model.addAttribute("user", this.getCurrentUser());
		
		return "student/dashboard";	
	}
	
}
