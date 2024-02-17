package com.opms.controllers.manage;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opms.controllers.BaseController;
import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.CourseDto;
import com.opms.enums.Actions;
import com.opms.services.ActivityService;
import com.opms.services.SubjectService;
import com.opms.utils.PaginationUtil;

@Controller
@RequestMapping("/admin")
public class ActivityController extends BaseController{
	
	private final ActivityService activityService;
	private final SubjectService subjectService;
	
	ActivityController(ActivityService activityService, SubjectService subjectService){
		this.activityService = activityService;
		this.subjectService = subjectService;
	}
	
	@GetMapping("/activities")
	public String activities(
			Model model , 
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword ) {
		
		model.addAttribute("user", getCurrentUser() );
		//List<ActivityDto> activities = activityService.findAllActivityByUserPaging( getCurrentUser().getId() , new PaginationUtil(offSet , limit , keyword));
		//Add comment
		//model.addAttribute("activities", activities);
		
		return "/admin/manage/activities";
	}
	
	@GetMapping("/activity-form")
	public String createForm(Model model , @RequestParam(required = false) Long id) {
		model.addAttribute("user", this.getCurrentUser() );
		model.addAttribute("subjects", subjectService.findAllByUser(getCurrentUser().getId()));
		if(id == null) {
			model.addAttribute("activity" , new ActivityDto());
			model.addAttribute("action" , Actions.SAVE);
		}else {
			model.addAttribute("activity" , activityService.get(id));
			model.addAttribute("action" , Actions.UPDATE);
		}
		
		return "/admin/manage/activity-form";
	}
}
