package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.opms.db.dtos.FolderDto;
import com.opms.db.dtos.ImageDto;
import com.opms.db.dtos.ModuleDto;
import com.opms.db.entities.Image;
import com.opms.db.entities.Modules;

public abstract class ModuleMapper extends BaseMapper implements Mapper<ModuleDto , Modules>{

	public ModuleMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public ModuleDto toDto(Modules entity) {
		return modelMapper.map(entity, ModuleDto.class);
	}

	@Override
	public Modules toEntity(ModuleDto dto) {
		return modelMapper.map(dto , Modules.class);
	}

	@Override
	public List<ModuleDto> toDtoList(List<Modules> entityList) {
		return entityList.stream().map( subject ->
    	modelMapper.map(subject, ModuleDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Modules> toEntityList(List<ModuleDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
