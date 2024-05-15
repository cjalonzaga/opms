package com.opms.controllers.student;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.FolderDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.UserFileDto;
import com.opms.services.FileService;
import com.opms.services.FolderService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/student")
public class StudentPorfolioController extends StudentBaseController{
	
	private final FolderService folderService;
	private final FileService fileService;
	
	StudentPorfolioController(FolderService folderService , FileService fileService){
		this.folderService = folderService;
		this.fileService = fileService;
	}
	
	@GetMapping("/portfolio")
	public String portfolio(Model model,
			@RequestParam(required = false) String success,
			@RequestParam(defaultValue="1")  Integer page,
			@RequestParam(defaultValue="10")  Integer size,
			@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String createdOn,
			@RequestParam(required = false) String mode) {
		
		StudentDto user = this.getCurrentUser();
		
		model.addAttribute("user", user);
		
		model.addAttribute("folder", new FolderDto());
		
		Pageable pageable = PageRequest.of(page - 1, size);
		
		if(mode.equalsIgnoreCase("folders")) {
			Page<FolderDto> paging;
			if(keyword == null && createdOn == null) {
				paging = folderService.findAllPageable(pageable , user.getId());
			}else {
				final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
				paging = folderService.filterSearch(createdDate, keyword , pageable);
			}
		
			model.addAttribute("folders",  paging.getContent());
			
			model.addAttribute("currentPage", paging.getNumber() + 1);
			model.addAttribute("totalItems", paging.getTotalElements());
	     	model.addAttribute("totalPages", paging.getTotalPages());
	     	model.addAttribute("pageSize", size);
		}
		
		if(mode.equalsIgnoreCase("files")) {
			Page<UserFileDto> paging;
			if(keyword == null && createdOn == null) {
				paging = fileService.findAllPageableByUser(pageable , user.getId());
			}else {
				final String createdDate = (createdOn == null || createdOn.isEmpty() ) ? null : createdOn;
				paging = fileService.filterSearch(null ,createdDate, keyword , pageable);
			}
			
			model.addAttribute("files",  paging.getContent());
			
			model.addAttribute("currentPage", paging.getNumber() + 1);
			model.addAttribute("totalItems", paging.getTotalElements());
	     	model.addAttribute("totalPages", paging.getTotalPages());
	     	model.addAttribute("pageSize", size);
		}
		
		return "student/manage/portfolio";
	}
	
	@PostMapping("/create-folder")
	public String createFolder(@ModelAttribute("folder") FolderDto folder) {
		
		Object obj = folderService.create(folder, this.getCurrentUser().getId() );
		String mode = "folders";
		String success = (obj != null) ? "?success=Folder created successfully" : "?error=Failed to create folder!";
		
		return "redirect:/student/portfolio"+success+"&mode="+mode;
	}
	
	@GetMapping("/folder")
	public String folderFiles(Model model, @RequestParam("folderId") Long folderId , @RequestParam(required = false) String msg ) {
		model.addAttribute("user", this.getCurrentUser());
		
		UserFileDto dto = new UserFileDto();
		dto.setFolderId(folderId);
		model.addAttribute("file", dto);
		
		Pageable pageable = PageRequest.of(1 - 1, 10);
		
		Page<UserFileDto> paging = fileService.findAllPageable(pageable , folderId);
		model.addAttribute("files",  paging.getContent());
		
		model.addAttribute("currentPage", paging.getNumber() + 1);
		model.addAttribute("totalItems", paging.getTotalElements());
     	model.addAttribute("totalPages", paging.getTotalPages());
     	model.addAttribute("pageSize", 10);
     	
		return "student/manage/folder";
	}
	
	@PostMapping("/uploadfile")
	public String uploadFolderFile(@ModelAttribute("file") UserFileDto dto ,@RequestParam("file") MultipartFile file) {
		UserFileDto ufile = fileService.upload(dto, this.getCurrentUser().getId() , file);
		String msg = (ufile != null) ? "&msg=Added new file "+ufile.getFileName() : "&msg=Failed to upload file";
		return "redirect:/student/folder?folderId="+dto.getFolderId()+msg;
	}
	
	@PostMapping("/deleteFile")
	public String deleteFile(@RequestParam("fileId") Long fileId , @RequestParam("folderId") Long folderId) {
		fileService.delete(fileId);
		return "redirect:/student/folder?folderId="+folderId;
	}
	
	@GetMapping(value="/downloadzip", produces="application/zip")
	public void downloadZip(@RequestParam("folderId") Long folderId, HttpServletResponse response) throws IOException {
		
		this.fileService.dowloadFolderFiles(folderId, response);
	}
	
	@PostMapping("/uploadDirect")
	public String uploadDirect(@RequestParam("file") MultipartFile file) {
		UserFileDto ufile = fileService.directUpload( this.getCurrentUser().getId() , file);
		String msg = (ufile != null) ? "&msg=Added new file "+ufile.getFileName() : "&msg=Failed to upload file";
		return "redirect:/student/portfolio?mode=files"+msg;
	}
	
	@PostMapping("/portfolio/delete")
	public String delete(@RequestParam(required = false , name="id") Long id ) {
		folderService.delete(id);
		return "redirect:/student/portfolio?mode=folders";
	}
	
	@PostMapping("/portfolio/deletefile")
	public String deleteFile(@RequestParam(required = false , name="id") Long id ) {
		fileService.delete(id);
		return "redirect:/student/portfolio?mode=files";
	}
}
