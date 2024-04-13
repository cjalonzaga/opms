package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.FolderDto;
import com.opms.db.entities.Activity;
import com.opms.db.entities.Folder;

public abstract class FolderMapper extends BaseMapper implements Mapper<FolderDto, Folder>{

	public FolderMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public FolderDto toDto(Folder entity) {
		return modelMapper.map(entity, FolderDto.class);
	}

	@Override
	public Folder toEntity(FolderDto dto) {
		return modelMapper.map(dto, Folder.class);
	}

	@Override
	public List<FolderDto> toDtoList(List<Folder> entityList) {
		return entityList.stream().map( subject ->
    	modelMapper.map(subject, FolderDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Folder> toEntityList(List<FolderDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
