package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.FolderDto;
import com.opms.db.dtos.ImageDto;
import com.opms.db.dtos.ModulesDto;
import com.opms.db.entities.Activity;
import com.opms.db.entities.Image;
import com.opms.db.entities.Modules;

public abstract class ModuleMapper extends BaseMapper implements Mapper<ModulesDto , Modules>{

	public ModuleMapper(ModelMapper modelMapper) {
		super(modelMapper);
		modelMapper.addMappings(mapProperty);
	}
	
	PropertyMap<Modules , ModulesDto> mapProperty = new PropertyMap<Modules , ModulesDto>() {
        protected void configure() {
           map().setSubjectName(source.getSubject().getCode());
       }
    };

	@Override
	public ModulesDto toDto(Modules entity) {
		return modelMapper.map(entity, ModulesDto.class);
	}

	@Override
	public Modules toEntity(ModulesDto dto) {
		return modelMapper.map(dto , Modules.class);
	}

	@Override
	public List<ModulesDto> toDtoList(List<Modules> entityList) {
		return entityList.stream().map( subject ->
    	modelMapper.map(subject, ModulesDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Modules> toEntityList(List<ModulesDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
