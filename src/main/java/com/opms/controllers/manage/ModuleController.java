package com.opms.controllers.manage;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opms.controllers.BaseController;
import com.opms.services.ModuleService;

@Controller
@RequestMapping("/admin")
public class ModuleController extends BaseController{
	
	private final ModuleService moduleService;
	
	public ModuleController(ModuleService moduleService){
		this.moduleService = moduleService;
	}
	
	@GetMapping("/modules")
	public String modules(Model model) {
		model.addAttribute("user", this.getCurrentUser());
		return "admin/manage/modules";
	}
}
