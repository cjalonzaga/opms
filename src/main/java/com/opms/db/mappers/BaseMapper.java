package com.opms.db.mappers;

import org.modelmapper.ModelMapper;

public abstract class BaseMapper {
	
	protected ModelMapper modelMapper;

	public BaseMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
    }
	
	public ModelMapper getModelMapper() {
		return modelMapper;
	}

	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
}
