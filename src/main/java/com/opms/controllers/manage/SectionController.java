package com.opms.controllers.manage;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opms.controllers.BaseController;
import com.opms.db.dtos.CourseDto;
import com.opms.db.dtos.SectionDto;
import com.opms.enums.Actions;
import com.opms.services.CourseService;
import com.opms.services.SectionService;

@Controller
@RequestMapping("/admin")
public class SectionController extends BaseController{
	
	private final SectionService sectionService;
	private final CourseService courseService;
	
	SectionController(SectionService sectionService, CourseService courseService){
		this.sectionService = sectionService;
		this.courseService = courseService;
	}
	
	@GetMapping("/sections")
	public String sections(Model model) {
		
		model.addAttribute("user", this.getCurrentUser());
		
		model.addAttribute("sections", sectionService.findAllByTeacher(this.getCurrentUser().getId()));
		
		return "admin/manage/sections";
	}
	
	@GetMapping("/section-form")
	public String createForm(Model model , @RequestParam(required = false) Long id) {
		model.addAttribute("user", this.getCurrentUser() );
		model.addAttribute("courses", courseService.getAll());
		if(id == null) {
			model.addAttribute("section" , new SectionDto());
			model.addAttribute("action" , Actions.SAVE);
		}else {
			model.addAttribute("section" , sectionService.get(id));
			model.addAttribute("action" , Actions.UPDATE);
		}
		
		return "admin/manage/section-form";
	}
	
	@PostMapping("/section-form/save")
	public String create(Model model , @ModelAttribute("section") SectionDto sectionDto) {
		sectionDto.setCreatedOn(LocalDateTime.now());
		SectionDto dto = sectionService.create(sectionDto, getCurrentUser().getId());
		String success = (dto != null) ? "true" : "false";
		
		return "redirect:/admin/sections?success="+success;
	}
	
	@PostMapping("/section-form/update")
	public String update(Model model , @ModelAttribute("section") SectionDto sectionDto) {
		SectionDto dto = sectionService.update(sectionDto, getCurrentUser().getId());
		String success = (dto != null) ? "true" : "false";
		
		return "redirect:/admin/sections?success="+success;
	}
	
}
