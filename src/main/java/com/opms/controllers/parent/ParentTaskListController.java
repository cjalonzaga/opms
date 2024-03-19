package com.opms.controllers.parent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opms.services.ActivityService;
import com.opms.services.AnswerService;
import com.opms.services.ParentService;
import com.opms.services.StudentService;

@Controller
@RequestMapping("/parent")
public class ParentTaskListController extends ParentBaseController{
	
	private final ParentService parentService;
	private final ActivityService activityService;
	private final StudentService studentService;
	private final AnswerService answerService;
	
	ParentTaskListController(ParentService parentService, ActivityService activityService, StudentService studentService, AnswerService answerService){
		this.parentService = parentService;
		this.activityService = activityService;
		this.studentService = studentService;
		this.answerService = answerService;
	}
	
	@GetMapping("/tasks")
	public String tasks(Model model , @RequestParam(required = false) Long id , @RequestParam(required = false) String mode) {
		
		if(id != null) {
			studentService.getById(id);
		}
		
		model.addAttribute("user",  this.getCurrentUser() );
		
		model.addAttribute("student",  studentService.getById(id) );
		
		if( mode.equalsIgnoreCase("activities") ) {
			model.addAttribute("activities", activityService.findActivitiesByStudent( id ));
		}
		
		return "parent/tasks";
	}
	
	@GetMapping("/task")
	public String indiviDualTask(Model model , 
			@RequestParam(required = false) Long id , @RequestParam(required = false) Long activityId) {
		model.addAttribute("user",  this.getCurrentUser() );
		
		model.addAttribute("activity", activityService.get(activityId));
		
		model.addAttribute("student",  studentService.getById(id) );
		
		model.addAttribute("answer", answerService.findByUser(id, activityId));
		
		return "parent/task";
	}
}
