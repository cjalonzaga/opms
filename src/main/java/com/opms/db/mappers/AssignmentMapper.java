package com.opms.db.mappers;

import java.util.List;

import org.modelmapper.ModelMapper;

import com.opms.db.dtos.AssignmentDto;
import com.opms.db.entities.Assignment;

public abstract class AssignmentMapper extends BaseMapper implements Mapper<AssignmentDto , Assignment>{

	public AssignmentMapper(ModelMapper modelMapper) {
		super(modelMapper);
	}

	@Override
	public AssignmentDto toDto(Assignment entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Assignment toEntity(AssignmentDto dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AssignmentDto> toDtoList(List<Assignment> entityList) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Assignment> toEntityList(List<AssignmentDto> dtoList) {
		// TODO Auto-generated method stub
		return null;
	}

}
