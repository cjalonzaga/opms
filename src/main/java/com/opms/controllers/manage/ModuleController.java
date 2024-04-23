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
import com.opms.db.dtos.ModulesDto;
import com.opms.db.dtos.SubjectDto;
import com.opms.enums.Actions;
import com.opms.services.ModuleService;
import com.opms.services.SectionService;
import com.opms.services.SubjectService;

@Controller
@RequestMapping("/admin")
public class ModuleController extends BaseController{
	
	private final ModuleService moduleService;
	private final SectionService sectionService;
	private final SubjectService subjectService;
	
	public ModuleController(ModuleService moduleService , SectionService sectionService , SubjectService subjectService){
		this.moduleService = moduleService;
		this.sectionService = sectionService;
		this.subjectService = subjectService;
	}
	
	@GetMapping("/modules")
	public String modules(Model model , 
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword) {
		
		model.addAttribute("user", getCurrentUser() );
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<ModulesDto> paging;
		
		if(keyword == null) {
			paging = moduleService.findAllByUserWithPaging(getCurrentUser().getId(), pageable);
		}else {
			//final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
			
			paging = moduleService.searchAllByUser(getCurrentUser().getId() , null , keyword, pageable);
		}
		
		model.addAttribute("modules",  paging.getContent());
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", size);
		
     	model.addAttribute("target", "/admin/modules");
     	
		return "admin/manage/modules";
	}
	
	@GetMapping("/module-form")
	public String create(Model model , @RequestParam(required = false) Long id) {
		
		model.addAttribute("user", this.getCurrentUser());
		model.addAttribute("sectionsAll", sectionService.getAll());
		
		model.addAttribute("subjects", subjectService.findAllByUser(this.getCurrentUser().getId()));
		
		if(id == null) {
			model.addAttribute("module" , new ModulesDto());
			model.addAttribute("action" , Actions.SAVE);
		}else {
			ModulesDto dto = moduleService.get(id);
			model.addAttribute("module" , moduleService.get(id));
			
			List<Long> forSections = dto.getSections().stream().map(
					sect -> sect.getId()
			 ).collect(Collectors.toList());
			
			model.addAttribute("sections", forSections);
			
			model.addAttribute("action" , Actions.UPDATE);
		}
		
		return "admin/manage/module-form";
	}
	
	@PostMapping("/module-form/save")
	public String create(@ModelAttribute("module") ModulesDto dto , @RequestParam("file") MultipartFile file) {
		
		ModulesDto obj = moduleService.create(dto, file , this.getCurrentUser().getId());
		
		String success = ( obj != null ) ? "true" : "false";
		
		return "redirect:/admin/modules?success="+success;
	}
	
	@PostMapping("/module/delete")
	public String delete(@RequestParam(required = false , name="id") Long id ) {
		moduleService.delete(id);
		return "redirect:/admin/modules";
	}
}
