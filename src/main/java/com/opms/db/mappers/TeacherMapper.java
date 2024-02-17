package com.opms.db.mappers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.TeacherDto;
import com.opms.db.dtos.UserDto;
import com.opms.db.entities.Teacher;
import com.opms.db.entities.User;

public abstract class TeacherMapper extends BaseMapper implements Mapper<TeacherDto , Teacher>{

	public TeacherMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}
	
	PropertyMap<Teacher , TeacherDto> skipProperty = new PropertyMap<Teacher , TeacherDto>() {
        protected void configure() {
           skip().setPassword(null);
           map().setImageUri(source.getImage().getUri());
           map().setFileName(source.getImage().getFileName());
       }
    };

	@Override
	public TeacherDto toDto(Teacher entity) {
		return modelMapper.map(entity , TeacherDto.class);
	}

	@Override
	public Teacher toEntity(TeacherDto dto) {
		return modelMapper.map(dto, Teacher.class);
	}

	@Override
	public List<TeacherDto> toDtoList(List<Teacher> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Teacher> toEntityList(List<TeacherDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
