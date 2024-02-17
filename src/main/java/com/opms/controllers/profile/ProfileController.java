package com.opms.controllers.profile;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opms.controllers.BaseController;
import com.opms.db.dtos.TeacherDto;
import com.opms.db.dtos.UserDto;
import com.opms.services.TeacherService;

@Controller
@RequestMapping("/admin")
public class ProfileController extends BaseController{
	
	private final TeacherService teacherService;
	
	public ProfileController(TeacherService teacherService){
		this.teacherService = teacherService;
	}
	
	@GetMapping("/profile")
	public String profile(Model model) {
		model.addAttribute("user", this.getCurrentUser() );
		return "admin/profile/profile";
	}
	
	@PostMapping("/profile/update/{userid}")
	public String updateProfile(Model model , @ModelAttribute("user") TeacherDto teacherDto, @RequestParam("file") MultipartFile file) {
		model.addAttribute("user", this.getCurrentUser() );
		if(file != null) {
			//imageService.upload(file, this.getCurrentUser());
			teacherService.update(file , teacherDto);
		}
		
		return "redirect:/admin/profile";
	}
//	
//	@PostMapping("/profile/update-password")
//	public String updateCredentials(@ModelAttribute("user") UserDto userDto , Model model) {
//		model.addAttribute("user", this.getCurrentUser() );
//		userService.updateCredentials(userDto);
//		return "redirect:/admin/profile";
//	}
}
