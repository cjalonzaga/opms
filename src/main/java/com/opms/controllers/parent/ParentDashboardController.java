package com.opms.controllers.parent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/parent")
public class ParentDashboardController extends ParentBaseController{
	
	@GetMapping("/dashboard")
	public String dashboard(Model model) {
		
		model.addAttribute("user", this.getCurrentUser() );
		
		return "parent/dashboard";
	}
}
