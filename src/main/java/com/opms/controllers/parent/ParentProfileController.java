package com.opms.controllers.parent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.ParentDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.services.ParentService;

@Controller
@RequestMapping("/parent")
public class ParentProfileController extends ParentBaseController{
	
	private final ParentService parentService;
	
	ParentProfileController (ParentService parentService){
		this.parentService = parentService;
	}
	
	@GetMapping("/profile")
	public String profile(Model model) {
		
		model.addAttribute("user", this.getCurrentUser());
		
		return "parent/profile";
	}
	
	@PostMapping("/profile/update/{userid}")
	public String updateProfile(Model model , @ModelAttribute("user") ParentDto parentDto, @RequestParam("file") MultipartFile file) {
		model.addAttribute("user", this.getCurrentUser() );
		parentService.updateProfile(file , parentDto);
		
		return "redirect:/parent/profile";
	}
	
	@PostMapping("/profile/update-password")
	public String updateCredentials(@ModelAttribute("user") ParentDto parentDto , Model model) {
		model.addAttribute("user", this.getCurrentUser() );
		parentService.updateCredentials(parentDto);
		
		return "redirect:/parent/profile";
	}
}
