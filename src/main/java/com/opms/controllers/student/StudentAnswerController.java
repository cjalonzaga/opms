package com.opms.controllers.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.AnswerDto;
import com.opms.services.ActivityService;
import com.opms.services.AnswerService;

@Controller
@RequestMapping("/student")
public class StudentAnswerController extends StudentBaseController{
	
	private final ActivityService activityService;
	private final AnswerService answerService;
	
	StudentAnswerController(ActivityService activityService , AnswerService answerService){
		this.activityService = activityService;
		this.answerService = answerService;
	}
	
	@GetMapping("/activity/task")
	public String task(Model model , @RequestParam(required = false) Long id) {
		model.addAttribute("user", this.getCurrentUser());
		model.addAttribute("activity", activityService.get(id));
		
		AnswerDto dto = answerService.findByUser( this.getCurrentUser().getId() , id );
		if(dto == null) {
			model.addAttribute("newAnswer", new AnswerDto());
		}else {
			model.addAttribute("answer", dto);
		}
		return "/student/manage/task";
	}
	
	@PostMapping("/activity/answer/save")
	public String submitActivity(@ModelAttribute("newAnswer") AnswerDto dto , @RequestParam("file") MultipartFile file) {
		
		AnswerDto data = answerService.create(dto, file);
		
		return "redirect:/student/activity/task?id="+data.getActivityId();
	}
}
