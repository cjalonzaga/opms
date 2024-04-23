package com.opms.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.opms.db.mappers.ModuleMapper;
import com.opms.repositories.ModuleRepository;
import com.opms.services.ModuleService;

@Service
public class ModuleServiceImpl extends ModuleMapper implements ModuleService{

	private final ModuleRepository moduleRepository;
	
	public ModuleServiceImpl(ModelMapper modelMapper , ModuleRepository moduleRepository) {
		super(modelMapper);
		this.moduleRepository = moduleRepository;
	}
	
}
