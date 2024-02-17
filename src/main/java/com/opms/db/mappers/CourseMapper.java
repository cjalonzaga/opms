package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;

import com.opms.db.dtos.CourseDto;
import com.opms.db.dtos.SubjectDto;
import com.opms.db.entities.Course;
import com.opms.db.entities.Subject;

public abstract class CourseMapper extends BaseMapper implements Mapper<CourseDto , Course>{

	public CourseMapper(ModelMapper modelMapper) {
		super(modelMapper);
		modelMapper.addMappings(mapProperty);
	}
	
	PropertyMap<Course , CourseDto> mapProperty = new PropertyMap<Course , CourseDto>() {
        protected void configure() {
           map().setCreatedBy(source.getTeacher().getFirstName());
       }
    };

	@Override
	public CourseDto toDto(Course entity) {
		return modelMapper.map(entity , CourseDto.class);
	}

	@Override
	public Course toEntity(CourseDto dto) {
		return modelMapper.map(dto , Course.class);
	}

	@Override
	public List<CourseDto> toDtoList(List<Course> entityList) {
		return entityList.stream().map( course -> 
    	modelMapper.map(course, CourseDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Course> toEntityList(List<CourseDto> dtoList) {
		return dtoList.stream().map(dto -> 
    	modelMapper.map(dto, Course.class)).collect(Collectors.toList());
	}
}
