package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.UserDto;
import com.opms.db.entities.User;

public abstract class UserMapper extends BaseMapper implements Mapper<UserDto , User> {

	public UserMapper(ModelMapper modelMapper) {
		super(modelMapper);
		modelMapper.addMappings(skipProperty); // don't include password field / set to null
	}
	
	PropertyMap<User , UserDto> skipProperty = new PropertyMap<User , UserDto>() {
        protected void configure() {
        	skip().setPassword(null);
//           map().setImageUri(source.getImage().getUri());
//           map().setFileName(source.getImage().getFileName());
       }
    };
    
    @Override
    public UserDto toDto(User entity) {
        return modelMapper.map(entity , UserDto.class);
    }

    @Override
    public User toEntity(UserDto dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public List<UserDto> toDtoList(List<User> entityList) {
        return entityList.stream().map( user -> 
        	modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<User> toEntityList(List<UserDto> dtoList) {
        return dtoList.stream().map(dto -> 
        	modelMapper.map(dto, User.class)).collect(Collectors.toList());
    }
}
