package com.opms.db.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import com.opms.db.dtos.ActivityDto;
import com.opms.db.dtos.AnswerDto;
import com.opms.db.entities.Activity;
import com.opms.db.entities.Answer;

public class AnswerMapper extends BaseMapper implements Mapper<AnswerDto , Answer>{

	public AnswerMapper(ModelMapper modelMapper) {
		super(modelMapper);
		modelMapper.addMappings(mapProperty);
	}
	
	PropertyMap<Answer , AnswerDto> mapProperty = new PropertyMap<Answer , AnswerDto>() {
        protected void configure() {
          map().setActivityId( source.getActivity().getId().toString() );
       }
    };

	@Override
	public AnswerDto toDto(Answer entity) {
		return modelMapper.map(entity, AnswerDto.class);
	}

	@Override
	public Answer toEntity(AnswerDto dto) {
		return modelMapper.map(dto, Answer.class);
	}

	@Override
	public List<AnswerDto> toDtoList(List<Answer> entityList) {
		return entityList.stream().map( answer ->
    	modelMapper.map(answer, AnswerDto.class)).collect(Collectors.toList());
	}

	@Override
	public List<Answer> toEntityList(List<AnswerDto> dtoList) {
		return dtoList.stream().map( answer ->
    	modelMapper.map(answer, Answer.class)).collect(Collectors.toList());
	}

}
