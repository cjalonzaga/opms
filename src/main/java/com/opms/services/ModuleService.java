package com.opms.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.ModulesDto;

public interface ModuleService {
	ModulesDto get(Long id);
	
	ModulesDto create(ModulesDto dto , MultipartFile file , Long teacherId);
	
	ModulesDto update(ModulesDto dto , MultipartFile file , Long teacherId);
	
	Page<ModulesDto> findAllByUserWithPaging(Long userId ,Pageable pageable);
	Page<ModulesDto> searchAllByUser(Long userId , String createdOn , String keyword , Pageable pageable);
	
	void delete(Long id);
	
	Page<ModulesDto> findByStudentSection(Long sectionId , Pageable pageable);
}
