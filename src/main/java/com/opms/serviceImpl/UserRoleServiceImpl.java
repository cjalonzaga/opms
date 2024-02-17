package com.opms.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.UserRoleDto;
import com.opms.db.entities.UserRole;
import com.opms.db.mappers.UserRoleMapper;
import com.opms.repositories.UserRoleRepository;
import com.opms.services.UserRoleService;

@Service
public class UserRoleServiceImpl extends UserRoleMapper implements UserRoleService{
	
	private final UserRoleRepository userRoleRepository;

	public UserRoleServiceImpl(ModelMapper modelMapper , UserRoleRepository userRoleRepository) {
		super(modelMapper);
		this.userRoleRepository = userRoleRepository;
	}

	@Override
	public UserRoleDto get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserRoleDto> getAll() {
		return this.toDtoList( (List<UserRole>) userRoleRepository.findAll() );
	}

	@Override
	public UserRoleDto create(UserRoleDto d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
	}

}
