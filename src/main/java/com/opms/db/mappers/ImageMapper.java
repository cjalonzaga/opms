package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;

import com.opms.db.dtos.FolderDto;
import com.opms.db.dtos.ImageDto;
import com.opms.db.entities.Image;

public abstract class  ImageMapper extends BaseMapper implements Mapper<ImageDto , Image>{

	public ImageMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public ImageDto toDto(Image entity) {
		return modelMapper.map(entity, ImageDto.class);
	}

	@Override
	public Image toEntity(ImageDto dto) {
		return modelMapper.map(dto , Image.class);
	}

	@Override
	public List<ImageDto> toDtoList(List<Image> entityList) {
		return entityList.stream().map( subject ->
    	modelMapper.map(subject, ImageDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Image> toEntityList(List<ImageDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
