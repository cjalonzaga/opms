package com.opms.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.opms.db.dtos.CourseDto;
import com.opms.db.entities.Course;
import com.opms.enums.CouncilType;
import com.opms.interfaces.PagingAware;

public interface CourseService extends CrudBaseService<CourseDto , Course> , PagingAware<CourseDto , Course>{
	CourseDto createWithUser(CourseDto courseDto, Long userId);
	CourseDto update(CourseDto courseDto);
	List<CourseDto> findAllByUser(Long userId);
	List<Course> findAllDistintByCouncil(CouncilType type);
}
