package com.opms.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.entities.Activity;
import com.opms.utils.PaginationUtil;

public interface ActivityService extends CrudBaseService<ActivityDto, Activity>{
	
	List<ActivityDto> findAllActivityByUserPaging(Long userId , PaginationUtil pagination);
}
