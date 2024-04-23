package com.opms.controllers.student;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opms.db.dtos.ModulesDto;
import com.opms.db.dtos.StudentDto;
import com.opms.services.ModuleService;

@Controller
@RequestMapping("/student")
public class StudentModulesController extends StudentBaseController{
	
	private final ModuleService moduleService;
	
	public StudentModulesController(ModuleService moduleService) {
		this.moduleService = moduleService;
	}
	
	@GetMapping("/modules")
	public String modules(Model model , @RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size) {
		StudentDto user = this.getCurrentUser();
		model.addAttribute("user", user);
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<ModulesDto> paging;
			
	    paging = moduleService.findByStudentSection( user.getStudentSection().getId() , pageable);
		
		
		model.addAttribute("modules",  paging.getContent());
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", size);
		
     	model.addAttribute("target", "/student/modules");
		
		return "/student/module/modules";
	}
	
	@GetMapping("/module-view")
	public String modules(Model model , @RequestParam(required = false) Long id) {
		
		StudentDto user = this.getCurrentUser();
		model.addAttribute("user", user);
		
		model.addAttribute("module", moduleService.get(id));
		
		return "/student/module/module-view";
	}
}
