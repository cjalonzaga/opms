package com.opms.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.opms.db.dtos.ParentDto;
import com.opms.db.dtos.StudentDto;
import com.opms.db.entities.Image;
import com.opms.db.entities.Parent;
import com.opms.db.entities.Student;
import com.opms.db.entities.Teacher;
import com.opms.db.entities.UserData;
import com.opms.db.mappers.ParentMapper;
import com.opms.enums.SignupStatus;
import com.opms.repositories.ImageRepository;
import com.opms.repositories.ParentRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.UserDataRepository;
import com.opms.services.ParentService;
import com.opms.utils.FileUtil;

@Service
public class ParentServiceImpl extends ParentMapper implements ParentService{

	private final ParentRepository parentRepository;
	private final StudentRepository studentRepository;
	private final PasswordEncoder passwordEncoder;
	private final UserDataRepository userDataRepository;
	private final ImageRepository imageRepository;
	private final AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
    private String bucketName;	
	
	public ParentServiceImpl(ModelMapper modelMapper , ParentRepository parentRepository , StudentRepository studentRepository,
			PasswordEncoder passwordEncoder , AmazonS3 s3Client , UserDataRepository userDataRepository , 
			ImageRepository imageRepository) {
		super(modelMapper);
		this.parentRepository = parentRepository;
		this.studentRepository = studentRepository;
		this.passwordEncoder = passwordEncoder;
		this.s3Client = s3Client;
		this.userDataRepository = userDataRepository;
		this.imageRepository = imageRepository;
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

	@Override
	public ParentDto updateProfile(MultipartFile file, ParentDto parentDto) {
		Parent user = parentRepository.findById(parentDto.getId()).get();
		String email = null;
		String password = null;
		if(parentDto.getEmail() == null && parentDto.getPassword() == null) {
			email = user.getEmail();
			password = user.getPassword();
		}
		
		Image image = null;
		if(!file.isEmpty()) {
			if(user.getImage() == null) {
				File fileObject = FileUtil.convertMultiPartFileToFile(file);
				String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
				s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
				String uri = s3Client.getUrl(bucketName, fileName).toString();
				
				image = new Image();
				image.setFileName(fileName);
				image.setOriginalFileName(file.getOriginalFilename());
				image.setUri(uri);
				image.setParent(user);
				
				user.setImage(image);
				
				imageRepository.save(image);
				
				fileObject.delete();
			}else {
				
				//check if image doesn't change to reduce api call
				if(!imageRepository.checkImageExist(file.getName())) {
					s3Client.deleteObject(bucketName , user.getImage().getFileName());

					File fileObject = FileUtil.convertMultiPartFileToFile(file);
					String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
					s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
					
					s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
					String uri = s3Client.getUrl(bucketName, fileName).toString();
					
					image = imageRepository.findById(user.getImage().getId()).get();
					image.setFileName(fileName);
					image.setUri(uri);
					image.setOriginalFileName(file.getOriginalFilename());
					user.setImage(image);
					
					imageRepository.save(image);
					
					fileObject.delete();
				}
			}
		}
		
		UserData data = userDataRepository.findById(user.getUserData().getId()).get();
		data.setProvince(parentDto.getUserData().getProvince());
		data.setCity(parentDto.getUserData().getCity());
		data.setCountry(parentDto.getUserData().getCountry());
		data.setZipCode(parentDto.getUserData().getZipCode());
		
		userDataRepository.save(data);
		
		user.setEmail(email);
		user.setPassword(password);
		user.setAddress(parentDto.getAddress());
		user.setFirstName(parentDto.getFirstName());
		user.setMiddleName(parentDto.getMiddleName());
		user.setLastName(parentDto.getLastName());
		
		
		return this.toDto( parentRepository.save(user) );
	}

	@Override
	public boolean verifyPassword(String password, Long userId) {
		return passwordEncoder.matches(password, parentRepository.findById(userId).get().getPassword());
	}

	@Override
	public ParentDto updateCredentials(ParentDto parentDto) {
		Parent user = parentRepository.findById(parentDto.getId()).get();
		UserData data = userDataRepository.findById(user.getUserData().getId()).get();
		user.setUserData(data);
		
		if( !passwordEncoder.matches(parentDto.getPassword() , user.getPassword() )){
			user.setPassword(passwordEncoder.encode( parentDto.getPassword()));
		}
		return this.toDto( parentRepository.save(user) );
	}
}
