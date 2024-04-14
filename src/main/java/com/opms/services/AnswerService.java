package com.opms.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.AnswerDto;

public interface AnswerService {
	AnswerDto create(AnswerDto dto , MultipartFile file);
	AnswerDto findByUser(Long userId, Long activityId);
	
	List<AnswerDto> findAllByActivity(Long activityId , Long sectionId);
	
	List<AnswerDto> findAllBySection(Long activityId , Long sectionId);
	
	AnswerDto updateAnswer(String status , Long id);
}
