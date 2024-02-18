package com.opms.services;

import java.util.List;

import com.opms.db.dtos.SectionDto;

public interface SectionService {
	SectionDto create(SectionDto dto , Long userId);
	List<SectionDto> findAllByTeacher(Long userId);
	SectionDto get(Long id);
	List<SectionDto> getAll();
	SectionDto update(SectionDto dto , Long userId);
}
