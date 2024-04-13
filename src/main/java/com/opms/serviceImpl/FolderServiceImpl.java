package com.opms.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.FolderDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.entities.Folder;
import com.opms.db.entities.Student;
import com.opms.db.mappers.FolderMapper;
import com.opms.repositories.FolderRepository;
import com.opms.repositories.StudentRepository;
import com.opms.services.FolderService;

@Service
public class FolderServiceImpl extends FolderMapper implements FolderService {
	
	private final FolderRepository folderRepository;
	private final StudentRepository studentRepository;

	public FolderServiceImpl(ModelMapper modelMapper , FolderRepository folderRepository , StudentRepository studentRepository) {
		super(modelMapper);
		this.folderRepository = folderRepository;
		this.studentRepository = studentRepository;
	}

	@Override
	public FolderDto create(FolderDto dto , Long studentId) {
		Folder folder = toEntity(dto);
		Student student = studentRepository.findById(studentId).get();
		folder.setStudent(student);
		folder.setCreatedOn( LocalDateTime.now() );
		
		return toDto( folderRepository.save(folder) );
	}

	@Override
	public Page<FolderDto> filterSearch(String createdOn, String keyword, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<FolderDto> list = toDtoList( folderRepository.findAllWithPaging(keyword, createdOn , offset, pageable.getPageSize()) );
		int totalSize = studentRepository.totalSize();
		return new PageImpl<>(list , pageable , totalSize );
	}

	@Override
	public Page<FolderDto> findAllPageable(Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<FolderDto> list = toDtoList(folderRepository.findAllPageable(offset, pageable.getPageSize() ));
		return new PageImpl<>(list , pageable , list.size() );
	}
	
}
