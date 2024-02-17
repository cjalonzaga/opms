package com.opms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.db.dtos.UserDto;
import com.opms.enums.UserRoles;
import com.opms.services.CourseService;
import com.opms.services.SectionService;
import com.opms.services.StudentService;
import com.opms.services.TeacherService;
import com.opms.services.UserRoleService;

@Controller
public class SignUpController{

	private final CourseService courseService;
	private final TeacherService teacherService;
	private final StudentService studentService;
	private final SectionService sectionService;
	
	SignUpController(CourseService courseService , 
			TeacherService teacherService , 
			StudentService studentService,
			SectionService sectionService){
		this.courseService = courseService;
		this.teacherService = teacherService;
		this.studentService = studentService;
		this.sectionService = sectionService;
	}
	
	@GetMapping("/signup")
	public String signUp(Model model , @RequestParam(required=false) String success , @RequestParam(required=false) String type) {
		if(type != null) {
			if(UserRoles.TEACHER == UserRoles.valueOf(type.toUpperCase())) {
				model.addAttribute("teacher" , new TeacherDto());
			}
			if(UserRoles.STUDENT == UserRoles.valueOf(type.toUpperCase())) {
				model.addAttribute("student" , new StudentDto());
				System.out.println("STUDENT");
				model.addAttribute("sections", sectionService.getAll());
			}
			if(UserRoles.PARENT == UserRoles.valueOf(type.toUpperCase())) {
				//model.addAttribute("parent" , new StudentDto());
			}
		}
		
		model.addAttribute("courses", courseService.getAll());
		return "signup";
	}
	
	@PostMapping("/signup/save/teacher")
	public String create(@ModelAttribute("teacher") TeacherDto teacherDto, Model model) {
		TeacherDto dto = teacherService.create(teacherDto);
		String success = (dto != null) ? "true" : "false";
		return "redirect:/signup?success="+success;
	}
	
	@PostMapping("/signup/save/student")
	public String createStudent(@ModelAttribute("student") StudentDto studentDto , Model model) {
		StudentDto dto = studentService.create(studentDto);
		String success = (dto != null) ? "true" : "false";
		return "redirect:/signup?success="+success;
	}
	
	@GetMapping("/signup/{id}/")
	public String success(Model model , @PathVariable("id") final Long id , @RequestParam("success") String success) {
		//model.addAttribute("user" , userService.get(id));
		return "signup";
	}
}
