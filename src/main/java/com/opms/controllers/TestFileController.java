package com.opms.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.UserFileDto;
import com.opms.db.entities.UserFile;
import com.opms.repositories.UserFileRepository;
import com.opms.services.FileService;

@Controller
@RequestMapping("/superadmin/test")
public class TestFileController {
	
	private final FileService fileService;
	private final UserFileRepository userFileRepository;
	
	public TestFileController(FileService fileService , UserFileRepository userFileRepository) {
		this.fileService = fileService;
		this.userFileRepository = userFileRepository;
	}
	
	@GetMapping("/file-upload")
	public String fileTest(Model model) {
		
		model.addAttribute("file", new UserFileDto());
		
		model.addAttribute("files", userFileRepository.findAll());
		
		return "superadmin/test";
		
	}
	
	@PostMapping("/test-upload")
	public String testUpload(@ModelAttribute("file") UserFileDto ufile , @RequestParam("file") MultipartFile file) {
		
		fileService.uploadLocal(ufile, file);
		
		return "redirect:/superadmin/test/file-upload";
	}
}
