package com.opms.controllers.manage;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opms.controllers.BaseController;
import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.CourseDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.enums.Actions;
import com.opms.services.ActivityService;
import com.opms.services.SectionService;
import com.opms.services.SubjectService;

@Controller
@RequestMapping("/admin")
public class ActivityController extends BaseController{
	
	private final ActivityService activityService;
	private final SubjectService subjectService;
	private final SectionService sectionService;
	
	ActivityController(ActivityService activityService, SubjectService subjectService , SectionService sectionService){
		this.activityService = activityService;
		this.subjectService = subjectService;
		this.sectionService = sectionService;
	}
	
	@GetMapping("/activities")
	public String activities(
			Model model , 
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword ) {
		
		model.addAttribute("user", getCurrentUser() );
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<ActivityDto> paging;
		
		if(keyword == null) {
			paging = activityService.findAllByUserWithPaging(getCurrentUser().getId(), pageable);
		}else {
			//final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
			
			paging = activityService.searchAllByUser(getCurrentUser().getId() , null , keyword, pageable);
		}
		
		model.addAttribute("activities",  paging.getContent());
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", size);
		
		return "/admin/manage/activities";
	}
	
	@GetMapping("/activity-form")
	public String createForm(Model model , @RequestParam(required = false) Long id) {
		TeacherDto user = this.getCurrentUser();
		model.addAttribute("user", user );
		model.addAttribute("subjects", subjectService.getAll());
		model.addAttribute("sectionsAll", sectionService.getAll());
		
		if(id == null) {
			model.addAttribute("activity" , new ActivityDto());
			model.addAttribute("action" , Actions.SAVE);
		}else {
			ActivityDto dto = activityService.get(id);
			model.addAttribute("activity" , dto);
			
			List<Long> forSections = dto.getSections().stream().map(
											sect -> sect.getId()
									 ).collect(Collectors.toList());
			
			model.addAttribute("sections", forSections);
			
			model.addAttribute("action" , Actions.UPDATE);
		}
		
		return "/admin/manage/activity-form";
	}
	
	@PostMapping("/activity-form/save")
	public String create(@ModelAttribute("activity") ActivityDto activityDto , @RequestParam("file") MultipartFile file) {
		
		ActivityDto dto = activityService.createByUser(activityDto, getCurrentUser().getId() , file);
		
		String success = (dto != null) ? "true" : "false";
		
		return "redirect:/admin/activities?success="+success;
	}
	
	@PostMapping("/activity-form/update")
	public String update(@ModelAttribute("activity") ActivityDto activityDto , @RequestParam("file") MultipartFile file) {
		
		ActivityDto dto = activityService.update(activityDto, getCurrentUser().getId() , file);
		String success = (dto != null) ? "true" : "false";
		
		return "redirect:/admin/activities?success="+success;
	}
}
