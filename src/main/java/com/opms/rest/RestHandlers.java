package com.opms.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.opms.db.dtos.ParentDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.services.CourseService;
import com.opms.services.ParentService;
import com.opms.services.StudentService;
import com.opms.services.TeacherService;
import com.opms.utils.JsonResponse;

@RestController
@RequestMapping("/ajax/")
public class RestHandlers {
	
	private final TeacherService teacherService;
	private final CourseService courseService;
	private final StudentService studentService;
	private final ParentService parentService;
	
	RestHandlers(TeacherService teacherService , CourseService courseService, StudentService studentService, ParentService parentService){
		this.teacherService = teacherService;
		this.courseService = courseService;
		this.studentService = studentService;
		this.parentService = parentService;
	}
	
	@GetMapping("verifyPassword/")
	public boolean verifyPassword(@RequestParam("password") String password, @RequestParam("userid") Long userId) {
		return teacherService.verifyPassword(password, userId);
	}
	
	@GetMapping("findStudent/")
	public StudentDto findStudent(@RequestParam("studentId") Long studentId) {
		return studentService.getById(studentId);
	}
	
	@GetMapping("update-status/")
	public JsonResponse updateStatus(@RequestParam("status") String status , 
				@RequestParam(required = true) Long id , 
				@RequestParam(required = false) String mode) {

		JsonResponse response = null;
		String message = "";
		if(mode.equals("student")) {
			response = new JsonResponse();
			StudentDto dto = studentService.update(id, status);
			message = (dto != null) ? "?id="+dto.getId()+"&success=Student status updated successfully!&mode="+mode : 
					"?id="+dto.getId()+"&error=Saved failed&mode="+mode;
			response.setMessage(message);
		}else if(mode.equals("parent")) {
			response = new JsonResponse();
			ParentDto dto = parentService.update(id, status);
			message = (dto != null) ? "?id="+dto.getId()+"&success=Student status updated successfully!&mode="+mode : 
					"?id="+dto.getId()+"&error=Saved failed&mode="+mode;
			response.setMessage(message);
			
		}else if(mode.equals("teacher")) {
			response = new JsonResponse();
			TeacherDto dto = teacherService.update(id, status);
			message = (dto != null) ? "?id="+dto.getId()+"&success=Student status updated successfully!&mode="+mode : 
					"?id="+dto.getId()+"&error=Saved failed&mode="+mode;
			response.setMessage(message);
		}
		response.setCallBackUri("/superadmin/edit");
		return response;
	}
}
