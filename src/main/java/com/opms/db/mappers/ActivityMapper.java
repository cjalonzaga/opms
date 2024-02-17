package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.SubjectDto;
import com.opms.db.entities.Activity;
import com.opms.db.entities.Subject;


public abstract class ActivityMapper extends BaseMapper implements Mapper<ActivityDto , Activity>{

	public ActivityMapper(ModelMapper modelMapper) {
		super(modelMapper);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ActivityDto toDto(Activity entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity toEntity(ActivityDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityDto> toDtoList(List<Activity> entityList) {
		return entityList.stream().map( subject ->
    	modelMapper.map(subject, ActivityDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Activity> toEntityList(List<ActivityDto> dtoList) {
		return dtoList.stream().map(dto -> 
    	modelMapper.map(dto, Activity.class)).collect(Collectors.toList());
	}

}
