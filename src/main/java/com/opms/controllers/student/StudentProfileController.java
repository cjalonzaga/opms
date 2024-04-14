package com.opms.controllers.student;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.services.StudentService;

@Controller
@RequestMapping("/student")
public class StudentProfileController extends StudentBaseController{
	private final StudentService studentService;
	
	public StudentProfileController(StudentService studentService) {
		this.studentService = studentService;
	}
	
	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("user", this.getCurrentUser() );
		return "student/profile/profile";
	}
	
	@PostMapping("/profile/update/{userid}")
	public String updateProfile(Model model , @ModelAttribute("user") StudentDto dto, @RequestParam("file") MultipartFile file) {
		model.addAttribute("user", this.getCurrentUser() );
		studentService.update(file , dto);
		
		return "redirect:/student/profile";
	}
	
	@PostMapping("/profile/update-password")
	public String updateCredentials(@ModelAttribute("user") StudentDto dto , Model model) {
		model.addAttribute("user", this.getCurrentUser() );
		studentService.updateCredentials(dto);
		
		return "redirect:/student/profile";
	}
}
