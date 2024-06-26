package com.opms.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.opms.db.dtos.SubjectDto;
import com.opms.db.entities.Subject;
import com.opms.interfaces.PagingAware;

public interface SubjectService extends CrudBaseService<SubjectDto , Subject>{
	List<SubjectDto> findAllByUser(Long userId);
	SubjectDto update(SubjectDto dto);
	SubjectDto createByUser(SubjectDto dto , Long userId);
	Page<SubjectDto> findAllByUserWithPaging(Long userId, Pageable pageable);
	Page<SubjectDto> searchAllByUser(Long userId, String createdOn, String keyword, String courseLevel , String sem , Pageable pageable);
	Page<SubjectDto> findAll(Pageable pageable);
	Page<SubjectDto> searchAll(String createdOn, String keyword, String courseLevel , String sem , Pageable pageable);
	List<SubjectDto> searchAllByStudent(Long userId);
}
