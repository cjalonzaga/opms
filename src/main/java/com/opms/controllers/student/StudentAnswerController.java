package com.opms.controllers.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opms.services.ActivityService;

@Controller
@RequestMapping("/student")
public class StudentAnswerController extends StudentBaseController{
	
	private final ActivityService activityService;
	
	StudentAnswerController(ActivityService activityService){
		this.activityService = activityService;
	}
	
	@GetMapping("/activity/task")
	public String task(Model model , @RequestParam(required = false) Long id) {
		model.addAttribute("user", this.getCurrentUser() );
		model.addAttribute("activity", activityService.get(id));
		
		return "/student/manage/task";
	}
}
