package com.opms.controllers.manage;

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

import com.opms.controllers.BaseController;
import com.opms.db.dtos.CourseDto;
import com.opms.db.dtos.SubjectDto;
import com.opms.enums.Actions;
import com.opms.services.CourseService;
import com.opms.services.SubjectService;

@Controller
@RequestMapping("/admin")
public class SubjectController extends BaseController{
	
	private final SubjectService subjectService;
	private final CourseService courseService;
	
	SubjectController(SubjectService subjectService , CourseService courseService){
		this.subjectService = subjectService;
		this.courseService = courseService;
	}
	
	@GetMapping("/subjects")
	public String subjects(Model model , 
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String createdOn,
			@RequestParam(required = false) String semester,
			@RequestParam(required = false) String courseLevel
			
		) {
		
		model.addAttribute("user", this.getCurrentUser() );
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<SubjectDto> paging;
		
		if(keyword == null && createdOn == null && semester == null && courseLevel == null ) {
			paging = subjectService.findAllByUserWithPaging( getCurrentUser().getId() , pageable);
		}else {
			final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
			final String level = (courseLevel == null || courseLevel.isEmpty() ) ? null : courseLevel;
			final String sem = (semester == null || semester.isEmpty() ) ? null : semester;
			paging = subjectService.searchAllByUser( getCurrentUser().getId() , createdDate, keyword, level, sem, pageable);
		}
		
		model.addAttribute("subjects", paging.getContent() );
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", size);
		
     	model.addAttribute("target", "/admin/subjects");
		
		return "admin/manage/subjects";
	}
	
	@GetMapping("/subject-form")
	public String createForm(Model model , @RequestParam(required = false) Long id) {
		model.addAttribute("user", this.getCurrentUser() );
		model.addAttribute("courseList", courseService.findAllByUser( this.getCurrentUser().getId() ));
		
		if(id == null) {
			
			model.addAttribute("subject" , new SubjectDto());
			model.addAttribute("action" , Actions.SAVE);
		}else {
			model.addAttribute("subject" , subjectService.get(id));
			model.addAttribute("action" , Actions.UPDATE);
		}
		
		return "admin/manage/subject-form";
	}
	
	@PostMapping("/subject-form/save")
	public String create(Model model , @ModelAttribute("subject") SubjectDto subjectDto) {
		SubjectDto dto = subjectService.create(subjectDto);
		String success = (dto != null) ? "true" : "false";
		
		return "redirect:/admin/subjects?success="+success;
	}
	
	@PostMapping("/subject-form/update")
	public String update(Model model , @ModelAttribute("subject") SubjectDto subjectDto) {
		SubjectDto dto = subjectService.update(subjectDto);
		String success = (dto != null) ? "true" : "false";
		return "redirect:/admin/subjects?success="+success;
	}
	
	@PostMapping("/subject/delete")
	public String delete(@RequestParam(required = false , name="id") Long id ) {
		subjectService.delete(id);
		return "redirect:/admin/subjects";
	}
}
