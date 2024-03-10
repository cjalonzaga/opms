package com.opms.services;

import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.AnswerDto;

public interface AnswerService {
	AnswerDto create(AnswerDto dto , MultipartFile file);
	AnswerDto findByUser(Long userId, Long activityId);
}
