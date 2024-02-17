package com.opms.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opms.services.CourseService;
import com.opms.services.TeacherService;

@RestController
@RequestMapping("/ajax/")
public class RestHandlers {
	
	private final TeacherService teacherService;
	private final CourseService courseService;
	
	RestHandlers(TeacherService teacherService , CourseService courseService){
		this.teacherService = teacherService;
		this.courseService = courseService;
	}
	
	@GetMapping("verifyPassword/")
	public boolean verifyPassword(@RequestParam("password") String password, @RequestParam("userid") Long userId) {
		return teacherService.verifyPassword(password, userId);
	}
	
}
