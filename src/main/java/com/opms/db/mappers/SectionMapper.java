package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.SectionDto;
import com.opms.db.dtos.UserDto;
import com.opms.db.entities.Section;

public class SectionMapper extends BaseMapper implements Mapper<SectionDto , Section> {

	public SectionMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public SectionDto toDto(Section entity) {
		return modelMapper.map(entity, SectionDto.class);
	}

	@Override
	public Section toEntity(SectionDto dto) {
		return modelMapper.map(dto, Section.class);
	}

	@Override
	public List<SectionDto> toDtoList(List<Section> entityList) {
		return entityList.stream().map( user -> 
    	modelMapper.map(user, SectionDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Section> toEntityList(List<SectionDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
