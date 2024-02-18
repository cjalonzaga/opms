package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.CourseDto;
import com.opms.db.dtos.SubjectDto;
import com.opms.db.entities.Activity;
import com.opms.db.entities.Course;
import com.opms.db.entities.Subject;


public abstract class ActivityMapper extends BaseMapper implements Mapper<ActivityDto , Activity>{

	public ActivityMapper(ModelMapper modelMapper) {
		super(modelMapper);
		modelMapper.addMappings(mapProperty);
	}
	
	PropertyMap<Activity , ActivityDto> mapProperty = new PropertyMap<Activity , ActivityDto>() {
        protected void configure() {
           map().setSubject(source.getSubject().getDescription());
       }
    };

	@Override
	public ActivityDto toDto(Activity entity) {
		return modelMapper.map(entity, ActivityDto.class);
	}

	@Override
	public Activity toEntity(ActivityDto dto) {
		return modelMapper.map(dto, Activity.class);
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
