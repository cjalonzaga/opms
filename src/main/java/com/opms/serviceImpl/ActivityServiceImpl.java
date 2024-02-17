package com.opms.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.entities.Activity;
import com.opms.db.mappers.ActivityMapper;
import com.opms.repositories.ActivityRepository;
import com.opms.services.ActivityService;
import com.opms.utils.PaginationUtil;

@Service
public class ActivityServiceImpl extends ActivityMapper implements ActivityService{
	
	private final ActivityRepository activityRepository;

	public ActivityServiceImpl(ModelMapper modelMapper , ActivityRepository activityRepository) {
		super(modelMapper);
		this.activityRepository = activityRepository;
	}

	@Override
	public ActivityDto get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityDto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityDto create(ActivityDto d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ActivityDto> findAllActivityByUserPaging(Long userId , PaginationUtil pagination) {
		
//		int offset = (pagination.getOffSet() != null ) ? pagination.getOffSet() * pagination.getLimit() : 0 * 3;
//		int limit = (pagination.getLimit() != null ) ? pagination.getLimit() : 3;
//		
//		List<Activity> activities = activityRepository.findAllActivityByUserPaging(userId, offset, limit);
		
		return null;
	}

}
