package com.opms.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.opms.db.dtos.UserFileDto;
import com.opms.db.entities.Folder;
import com.opms.db.entities.Student;
import com.opms.db.entities.UserFile;
import com.opms.db.mappers.UserFileMapper;
import com.opms.repositories.FolderRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.UserFileRepository;
import com.opms.services.FileService;
import com.opms.services.FolderService;
import com.opms.utils.FileUtil;

@Service
public class FileServiceImpl extends UserFileMapper implements FileService{
	
	private final StudentRepository studentRepository;
	private final FolderRepository folderRepository;
	private final UserFileRepository userFileRepository;
	private final AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
    private String bucketName;
	
	public FileServiceImpl(ModelMapper modelMapper , 
			StudentRepository studentRepository,
			UserFileRepository userFileRepository,
			AmazonS3 s3Client,
			FolderRepository folderRepository) {
		super(modelMapper);
		this.studentRepository = studentRepository;
		this.userFileRepository = userFileRepository;
		this.s3Client = s3Client;
		this.folderRepository = folderRepository;
	}

	@Override
	public UserFileDto upload(UserFileDto dto , Long userId , MultipartFile file) {
		if(dto == null && file.isEmpty()) {
			return null;
		}
		
		UserFile entity = toEntity(dto);
		Folder folder = folderRepository.findById(dto.getFolderId()).get();
		Student s = studentRepository.findById(userId).get();
		entity.setFolder(folder);
		entity.setStudent(s);
		entity.setOriginalFileName(file.getOriginalFilename());
		entity.setCreatedOn(LocalDateTime.now());
		entity.setUpdatedOn(LocalDateTime.now());
		if(!file.isEmpty()) {
			File fileObject = FileUtil.convertMultiPartFileToFile(file);
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
			String uri = s3Client.getUrl(bucketName, fileName).toString();
			
			entity.setFileName(fileName);
			entity.setType(FileUtil.getFileType(fileName));
			entity.setUri(uri);
			
			fileObject.delete();
		}
		
		return toDto(userFileRepository.save(entity));
	}

	@Override
	public Page<UserFileDto> filterSearch(String status, String createdOn, String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserFileDto> findAllPageable(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long fileId) {
		UserFile file = userFileRepository.findById(fileId).get();
		s3Client.deleteObject(bucketName , file.getFileName() );
		
		userFileRepository.deleteById(file.getId());
	}

	@Override
	public Page<UserFileDto> findAllPageable(Pageable pageable, Long folderId) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<UserFileDto> list = toDtoList( userFileRepository.findAllPageable(offset, pageable.getPageSize() , folderId));
		return new PageImpl<>(list , pageable , list.size() );
	}
	
	

}
