package com.opms.controllers.superadmin;

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

import com.opms.db.dtos.CourseDto;
import com.opms.db.dtos.ParentDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.SuperAdminDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.enums.SignupStatus;
import com.opms.services.ParentService;
import com.opms.services.StudentService;
import com.opms.services.SuperAdminService;
import com.opms.services.TeacherService;

@Controller
@RequestMapping("/superadmin")
public class SuperAdminController extends SuperAdminBaseController{
	
	private final SuperAdminService superAdminService;
	private final StudentService studentService;
	private final ParentService parentService;
	private final TeacherService teacherService;
	
	SuperAdminController(SuperAdminService superAdminService , 
			 StudentService studentService,
			 ParentService parentService,
			 TeacherService teacherService){
		this.superAdminService = superAdminService;
		this.studentService = studentService;
		this.parentService = parentService;
		this.teacherService = teacherService;
	}
	
	@GetMapping("/dashboard")
	public String superAdmin(Model model) {
		
		model.addAttribute("user", this.getCurrentUser() );
		
		return "superadmin/dashboard";
	}
	
	@GetMapping("/students")
	public String superAdminStudents(Model model , 
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String createdOn,
			@RequestParam(required = false) String status) {
		
		SuperAdminDto user = this.getCurrentUser();
		model.addAttribute("user", user );
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<StudentDto> paging;
		if(keyword == null && createdOn == null) {
			paging = studentService.findAllPageable(pageable);
		}else {
			final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
			
			paging = studentService.filterSearch(status, createdDate, keyword , pageable);
		}
		
		model.addAttribute("students",  paging.getContent());
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", size);
     	
     	model.addAttribute("target", "/superadmin/students");
		
		return "superadmin/students";
	}
	
	@GetMapping("/parents")
	public String superAdminParents(Model model , 
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String createdOn,
			@RequestParam(required = false) String status) {
		
		SuperAdminDto user = this.getCurrentUser();
		model.addAttribute("user", user );
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<ParentDto> paging;
		if(keyword == null && createdOn == null) {
			paging = parentService.findAllPageable(pageable);
		}else {
			final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
			
			paging = parentService.filterSearch(status, createdDate, keyword , pageable);
		}
		
		model.addAttribute("parents",  paging.getContent());
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", size);
     	
     	model.addAttribute("target", "/superadmin/parents");
		
		return "superadmin/parents";
	}
	
	@GetMapping("/teachers")
	public String superAdminTeachers(Model model , 
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String createdOn,
			@RequestParam(required = false) String status) {
		
		SuperAdminDto user = this.getCurrentUser();
		model.addAttribute("user", user );
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		Page<TeacherDto> paging;
		if(keyword == null && createdOn == null) {
			paging = teacherService.findAllPageable(pageable);
		}else {
			final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
			
			paging = teacherService.filterSearch(status, createdDate, keyword , pageable);
		}
		
		model.addAttribute("teachers",  paging.getContent());
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", size);
     	
     	model.addAttribute("target", "/superadmin/teachers");
		
		return "superadmin/teachers";
	}
	
	@GetMapping("/edit")
	public String getEditForm(Model model ,
			@RequestParam(required = false) Long id ,
			@RequestParam(required = false) String success , 
			@RequestParam(required = false) String error ,
			@RequestParam(required = false) String mode) {
		
		model.addAttribute("user", this.getCurrentUser() );
		model.addAttribute("status", SignupStatus.values() );
		Object obj = null;
		if(mode.equals("student")) {
			obj = studentService.getById(id);
			model.addAttribute("editUser", obj);
			model.addAttribute("action", "students");
		}else if(mode.equals("parent")) {
			obj = parentService.getById(id);
			model.addAttribute("editUser", parentService.getById(id));
			model.addAttribute("action", "parents");
		}else if(mode.equals("teacher")) {
			obj = teacherService.getById(id);
			model.addAttribute("editUser", obj);
			model.addAttribute("action", "teachers");
		}
		
		return "superadmin/edit";
	}
	
	@PostMapping("/delete")
	public String delete(@RequestParam(required = false) Long id , @RequestParam(required = false) String mode) {
		String msg = "";
		if(mode.equals("students")) {
			msg = studentService.delete(id);
		}else if(mode.equals("parents")) {
			msg = parentService.delete(id);
		}else if(mode.equals("teachers")) {
			msg = teacherService.delete(id);
		}
		return "redirect:/superadmin/" + mode + "?success="+msg;
	}
}
