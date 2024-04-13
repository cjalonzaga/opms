package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.SuperAdminDto;
import com.opms.db.entities.Student;
import com.opms.db.entities.SuperAdmin;

public abstract class SuperAdminMapper extends BaseMapper implements Mapper<SuperAdminDto , SuperAdmin>{

	public SuperAdminMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}
	
	PropertyMap<SuperAdmin , SuperAdminDto> skipProperty = new PropertyMap<SuperAdmin , SuperAdminDto>() {
        protected void configure() {
           skip().setPassword(null);
           map().setImageUri(source.getImage().getUri());
           map().setFileName(source.getImage().getFileName());
       }
    };

	@Override
	public SuperAdminDto toDto(SuperAdmin entity) {
		return modelMapper.map(entity , SuperAdminDto.class);
	}

	@Override
	public SuperAdmin toEntity(SuperAdminDto dto) {
		return modelMapper.map(dto, SuperAdmin.class);
	}

	@Override
	public List<SuperAdminDto> toDtoList(List<SuperAdmin> entityList) {
		return entityList.stream().map( user -> 
    	modelMapper.map(user, SuperAdminDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<SuperAdmin> toEntityList(List<SuperAdminDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
