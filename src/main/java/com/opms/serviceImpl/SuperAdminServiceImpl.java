package com.opms.serviceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.SuperAdminDto;
import com.opms.db.mappers.SuperAdminMapper;
import com.opms.repositories.SuperAdminRepository;
import com.opms.services.SuperAdminService;

@Service
public class SuperAdminServiceImpl extends SuperAdminMapper implements SuperAdminService {
	
	private final SuperAdminRepository superAdminRepository;

	public SuperAdminServiceImpl(ModelMapper modelMapper , SuperAdminRepository superAdminRepository) {
		super(modelMapper);
		this.superAdminRepository = superAdminRepository;
	}

	@Override
	public SuperAdminDto findByUsername(String username) {
		return toDto( superAdminRepository.findByUsername(username) );
	}

}
