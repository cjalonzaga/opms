package com.opms.controllers.manage;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opms.controllers.BaseController;
import com.opms.db.dtos.CourseDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.db.dtos.UserDto;
import com.opms.enums.Actions;
import com.opms.services.CourseService;
import com.opms.services.SectionService;

@Controller
@RequestMapping("/admin")
public class CourseController extends BaseController{
	
	private final CourseService courseService;
	private final SectionService sectionService;
	
	CourseController(CourseService courseService , SectionService sectionService){
		this.courseService = courseService;
		this.sectionService = sectionService;
	}
	
	@GetMapping("/courses")
	public String courses(
			Model model ,
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String createdOn
		) {
		
		TeacherDto user = this.getCurrentUser();
		model.addAttribute("user", user );
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<CourseDto> paging;
		
		if(keyword == null && createdOn == null) {
			paging = courseService.findAllByUserWithPaging(pageable);
		}else {
			final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
			
			paging = courseService.searchAllByUser(createdDate , keyword, pageable);
		}
		
		model.addAttribute("courses",  paging.getContent());
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", size);
		
     	model.addAttribute("target", "/admin/courses");
     	
		return "admin/manage/courses";
	}
	
	@GetMapping("/course-form")
	public String createForm(Model model , @RequestParam(required = false) Long id) {
		model.addAttribute("user", this.getCurrentUser() );
		model.addAttribute("sections", sectionService.findAllByTeacher(getCurrentUser().getId()));
		if(id == null) {
			model.addAttribute("course" , new CourseDto());
			model.addAttribute("action" , Actions.SAVE);
		}else {
			model.addAttribute("course" , courseService.get(id));
			model.addAttribute("action" , Actions.UPDATE);
		}
		
		return "admin/manage/course-form";
	}
	
	@PostMapping("/course-form/save")
	public String create(Model model , @ModelAttribute("course") CourseDto courseDto) {
		courseDto.setCreatedOn(LocalDateTime.now());
		CourseDto dto = courseService.createWithUser(courseDto, getCurrentUser().getId());
		String success = (dto != null) ? "true" : "false";
		
		return "redirect:/admin/courses?success="+success;
	}
	
	@PostMapping("/course-form/update")
	public String update(@ModelAttribute("course") CourseDto courseDto) {
		CourseDto dto = courseService.update(courseDto);
		//need to edit impl to return null if update error
		String success = (dto != null) ? "true" : "false";
		return "redirect:/admin/courses?success="+success;
	}
	
	@PostMapping("/courses/delete")
	public String delete(@RequestParam(required = false , name="id") Long id ) {
		courseService.delete(id);
		return "redirect:/admin/courses";
	}
}
