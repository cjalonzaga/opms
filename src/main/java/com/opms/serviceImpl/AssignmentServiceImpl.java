package com.opms.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.AssignmentDto;
import com.opms.db.mappers.AssignmentMapper;
import com.opms.services.AssignmentService;

@Service
public class AssignmentServiceImpl extends AssignmentMapper implements AssignmentService{

	public AssignmentServiceImpl(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public AssignmentDto get(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssignmentDto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AssignmentDto create(AssignmentDto d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
