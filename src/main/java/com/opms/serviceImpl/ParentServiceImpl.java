package com.opms.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.ParentDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.entities.Parent;
import com.opms.db.entities.Student;
import com.opms.db.mappers.ParentMapper;
import com.opms.enums.SignupStatus;
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
		
		if(parentRepository.ifUserExist(dto.getUsername())) {
			return null;
		}
		
		if(!dto.getStudentIds().isEmpty()) {
			List<Student> studentList = studentRepository.findAllById( dto.getStudentIds() );
			parent.setStudents(studentList);
		}
		parent.setCreatedOn(LocalDateTime.now());
		parent.setIsActivated(Boolean.FALSE);
		parent.setStatus(SignupStatus.NEW);
		
		parent.setPassword( passwordEncoder.encode(dto.getPassword()) );
		
		return toDto( parentRepository.save(parent) );
	}

	@Override
	public ParentDto findByUsername(String username) {
		return toDto( parentRepository.findByUsername(username) );
	}

	@Override
	public Page<ParentDto> filterSearch(String status, String createdOn, String keyword, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<ParentDto> list = toDtoList( parentRepository.findAllWithPaging(keyword, createdOn , status , offset, pageable.getPageSize()) );
		
		int totalSize = parentRepository.totalSize();
		
		return new PageImpl<>(list , pageable , totalSize );
	}

	@Override
	public Page<ParentDto> findAllPageable(Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<ParentDto> list = toDtoList(parentRepository.findAllPageable(offset, pageable.getPageSize() ));
		return new PageImpl<>(list , pageable , list.size() );
	}

	@Override
	public ParentDto getById(Long id) {
		return toDto (parentRepository.findById(id).orElseThrow(null));
	}

	@Override
	public ParentDto update(Long id, String status) {
		Parent parent = parentRepository.findById(id).get();
		if(parent == null ) {
			return null;
		}else {
			parent.setStatus(SignupStatus.valueOf(status));
			if(SignupStatus.valueOf(status) == SignupStatus.APPROVED) {
				parent.setIsActivated(true);
			}else {
				parent.setIsActivated(false);
			}
			return toDto ( parentRepository.save(parent));
		}
	}
	
	@Override
	public String delete(Long id) {
		if(id == null) {
			return "Failed to delete user.";
		}
		
		Parent t = parentRepository.findById(id).get();
		t.setIsValid(false);
		t.setIsActivated(false);
		
		parentRepository.save(t);
		
		return "User successfully deleted.";
	}
}
