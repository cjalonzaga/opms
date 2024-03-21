package com.opms.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.entities.Activity;
import com.opms.utils.PaginationUtil;

public interface ActivityService extends CrudBaseService<ActivityDto, Activity>{
	
	List<ActivityDto> findAllActivityByUserPaging(Long userId , PaginationUtil pagination);
	ActivityDto createByUser(ActivityDto dto , Long userId , MultipartFile file);
	
	Page<ActivityDto> findAllByUserWithPaging(Long userId ,Pageable pageable);
	Page<ActivityDto> searchAllByUser(Long userId , String createdOn , String keyword , Pageable pageable);
	
	List<ActivityDto> findActivitiesByStudent(Long studentId);
	
	ActivityDto update(ActivityDto dto , Long userId , MultipartFile file);
	
}
