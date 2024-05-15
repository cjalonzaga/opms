package com.opms.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.opms.db.dtos.SectionDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.services.ActivityService;
import com.opms.services.SectionService;
import com.opms.services.StudentService;
import com.opms.services.TeacherService;

@Controller
@RequestMapping("/admin")
public class DashboardController extends BaseController{
	
	
	private final TeacherService teacherService;
	private final SectionService sectionService;
	private final ActivityService activityService;
	private final StudentService studentService;
	
	DashboardController(TeacherService teacherService , SectionService sectionService , ActivityService activityService , StudentService studentService){
		this.teacherService = teacherService;
		this.sectionService = sectionService;
		this.activityService = activityService;
		this.studentService = studentService;
	}
	
	@GetMapping("/dashboard")
    public String home(Model model) throws JsonProcessingException {
		TeacherDto user = this.getCurrentUser();
        model.addAttribute("user" , user);
        
        model.addAttribute("sections", sectionService.getAll());
        
        Map<Long , Integer> map = new HashMap<>();
        for(SectionDto dto : sectionService.getAll()) {
        	map.put(dto.getId(), studentService.countStudentBySection(dto.getId()));
        }
        
        model.addAttribute("sect", map );
        
        model.addAttribute("summary", activityService.getSummary(user.getId()));
        
        return "dashboard";
	}
	
}
