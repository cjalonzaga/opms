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
import com.opms.db.entities.UserFile;
import com.opms.db.mappers.FolderMapper;
import com.opms.repositories.FolderRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.UserFileRepository;
import com.opms.services.FolderService;

@Service
public class FolderServiceImpl extends FolderMapper implements FolderService {
	
	private final FolderRepository folderRepository;
	private final StudentRepository studentRepository;
	private final UserFileRepository userFileRepository;

	public FolderServiceImpl(ModelMapper modelMapper , FolderRepository folderRepository , StudentRepository studentRepository , 
			UserFileRepository userFileRepository) {
		super(modelMapper);
		this.folderRepository = folderRepository;
		this.studentRepository = studentRepository;
		this.userFileRepository = userFileRepository;
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
	public Page<FolderDto> findAllPageable(Pageable pageable, Long studentId) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<FolderDto> list = toDtoList(folderRepository.findAllPageable(offset, pageable.getPageSize() , studentId ));
		return new PageImpl<>(list , pageable , list.size() );
	}

	@Override
	public void delete(Long id) {
		Folder folder = folderRepository.findById(id).get();
		folder.setIsValid(Boolean.FALSE);
		
		List<UserFile> list = userFileRepository.findAllByFolder(folder.getId());
		for(UserFile file : list) {
			file.setIsValid(false);
			userFileRepository.save(file);
		}
		
		folderRepository.save(folder);
	}
	
}
