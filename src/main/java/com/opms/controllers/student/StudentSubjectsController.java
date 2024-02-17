package com.opms.controllers.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opms.services.SubjectService;

@Controller
@RequestMapping("/student")
public class StudentSubjectsController extends StudentBaseController{
	
	private final SubjectService subjectService;
	
	StudentSubjectsController(SubjectService subjectService){
		this.subjectService = subjectService;
	}
	
	@GetMapping("/mysubjects")
	public String mySubjects(Model model) {
		model.addAttribute("user", this.getCurrentUser());
		
		model.addAttribute("subjects", subjectService.searchAllByStudent(this.getCurrentUser().getId()));
		
		return "/student/manage/mysubjects";
	}
	
}
