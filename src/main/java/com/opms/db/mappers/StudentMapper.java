package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.SectionDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.db.entities.Student;
import com.opms.db.entities.Teacher;

public abstract class StudentMapper extends BaseMapper implements Mapper<StudentDto , Student>{

	public StudentMapper(ModelMapper modelMapper) {
		super(modelMapper);
		modelMapper.addMappings(skipProperty);
	}
	
	PropertyMap<Student , StudentDto> skipProperty = new PropertyMap<Student , StudentDto>() {
        protected void configure() {
           skip().setPassword(null);
           map().setImageUri(source.getImage().getUri());
           map().setFileName(source.getImage().getFileName());
           map().setSectionName(source.getSection());
           map().setStudentSection(source.getStudentSection());
       }
    };

	@Override
	public StudentDto toDto(Student entity) {
		return modelMapper.map(entity , StudentDto.class);
	}

	@Override
	public Student toEntity(StudentDto dto) {
		return modelMapper.map(dto, Student.class);
	}

	@Override
	public List<StudentDto> toDtoList(List<Student> entityList) {
		return entityList.stream().map( user -> 
    	modelMapper.map(user, StudentDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Student> toEntityList(List<StudentDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
