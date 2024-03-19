package com.opms.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.ParentDto;
import com.opms.db.entities.Parent;
import com.opms.db.entities.Student;
import com.opms.db.mappers.ParentMapper;
import com.opms.repositories.ParentRepository;
import com.opms.repositories.StudentRepository;
import com.opms.services.ParentService;

@Service
public class ParentServiceImpl extends ParentMapper implements ParentService{

	private final ParentRepository parentRepository;
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;
	
	public ParentServiceImpl(ModelMapper modelMapper , ParentRepository parentRepository , StudentRepository studentRepository,
			PasswordEncoder passwordEncoder) {
		super(modelMapper);
		this.parentRepository = parentRepository;
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public ParentDto create(ParentDto dto, List<Long> studentIds) {
		
		Parent parent = toEntity(dto);
		if(!dto.getStudentIds().isEmpty()) {
			List<Student> studentList = studentRepository.findAllById( dto.getStudentIds() );
			parent.setStudents(studentList);
		}
		
		parent.setPassword( passwordEncoder.encode(dto.getPassword()) );
		
		return toDto( parentRepository.save(parent) );
	}

	@Override
	public ParentDto findByUsername(String username) {
		return toDto( parentRepository.findByUsername(username) );
	}
	
}
