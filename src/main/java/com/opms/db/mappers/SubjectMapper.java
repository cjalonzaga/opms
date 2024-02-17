package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.SubjectDto;
import com.opms.db.dtos.UserDto;
import com.opms.db.entities.Subject;
import com.opms.db.entities.User;

public abstract class SubjectMapper extends BaseMapper implements Mapper<SubjectDto , Subject> {

	public SubjectMapper(ModelMapper modelMapper) {
		super(modelMapper);
		modelMapper.addMappings(mapProperty);
	}
	
	PropertyMap<Subject , SubjectDto> mapProperty = new PropertyMap<Subject , SubjectDto>() {
        protected void configure() {
           map().setCourseName(source.getCourse().getName());
       }
    };

	@Override
	public SubjectDto toDto(Subject entity) {
		return modelMapper.map(entity , SubjectDto.class);
	}

	@Override
	public Subject toEntity(SubjectDto dto) {
		return modelMapper.map(dto , Subject.class);
	}

	@Override
	public List<SubjectDto> toDtoList(List<Subject> entityList) {
		return entityList.stream().map( subject ->
    	modelMapper.map(subject, SubjectDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Subject> toEntityList(List<SubjectDto> dtoList) {
		return dtoList.stream().map(dto -> 
    	modelMapper.map(dto, Subject.class)).collect(Collectors.toList());
	}

}
