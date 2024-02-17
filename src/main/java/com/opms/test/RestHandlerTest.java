package com.opms.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.opms.db.dtos.CourseDto;
import com.opms.services.CourseService;

@RestController
@RequestMapping("/api/")
public class RestHandlerTest {
	
	@Autowired
	private CourseService courseService;
	
	@PostMapping("create-1")
	public CourseDto create(@RequestBody CourseDto course) {
		System.out.println("create test");
		return courseService.create(course);
	}
	
}
