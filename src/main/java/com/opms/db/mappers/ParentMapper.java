package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.opms.db.dtos.ParentDto;
import com.opms.db.entities.Parent;

public abstract class ParentMapper extends BaseMapper implements Mapper<ParentDto , Parent>{

	public ParentMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public ParentDto toDto(Parent entity) {
		return modelMapper.map(entity, ParentDto.class);
	}

	@Override
	public Parent toEntity(ParentDto dto) {
		return modelMapper.map(dto, Parent.class);
	}

	@Override
	public List<ParentDto> toDtoList(List<Parent> entityList) {
		return entityList.stream().map( subject ->
    	modelMapper.map(subject, ParentDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Parent> toEntityList(List<ParentDto> dtoList) {
		return dtoList.stream().map(dto -> 
    	modelMapper.map(dto, Parent.class)).collect(Collectors.toList());
	}

}
