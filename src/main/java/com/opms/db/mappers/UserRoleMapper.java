package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.opms.db.dtos.UserDto;
import com.opms.db.dtos.UserRoleDto;
import com.opms.db.entities.UserRole;

public abstract class UserRoleMapper extends BaseMapper implements Mapper<UserRoleDto, UserRole>{

	public UserRoleMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public UserRoleDto toDto(UserRole entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserRole toEntity(UserRoleDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleDto> toDtoList(List<UserRole> entityList) {
		 return entityList.stream().map( userRole -> 
     	modelMapper.map(userRole, UserRoleDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<UserRole> toEntityList(List<UserRoleDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
