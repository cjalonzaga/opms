package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.AnswerDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.UserFileDto;
import com.opms.db.entities.Answer;
import com.opms.db.entities.Student;
import com.opms.db.entities.UserFile;

public class UserFileMapper extends BaseMapper implements Mapper<UserFileDto , UserFile>{

	public UserFileMapper(ModelMapper modelMapper) {
		super(modelMapper);
		modelMapper.addMappings(mapProperty);
	}

	PropertyMap<UserFile , UserFileDto> mapProperty = new PropertyMap<UserFile , UserFileDto>() {
        protected void configure() {
          map().setFolderId( source.getFolder().getId() );
       }
    };
	
	@Override
	public UserFileDto toDto(UserFile entity) {
		return modelMapper.map(entity , UserFileDto.class);
	}

	@Override
	public UserFile toEntity(UserFileDto dto) {
		return modelMapper.map(dto, UserFile.class);
	}

	@Override
	public List<UserFileDto> toDtoList(List<UserFile> entityList) {
		return entityList.stream().map( user -> 
    	modelMapper.map(user, UserFileDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<UserFile> toEntityList(List<UserFileDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
