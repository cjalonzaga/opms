package com.opms.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.opms.db.dtos.StudentDto;
import com.opms.db.dtos.TeacherDto;
import com.opms.db.entities.Image;
import com.opms.db.entities.Student;
import com.opms.db.entities.Teacher;
import com.opms.db.entities.UserData;
import com.opms.db.mappers.TeacherMapper;
import com.opms.enums.SignupStatus;
import com.opms.enums.UserRoles;
import com.opms.repositories.CourseRepository;
import com.opms.repositories.ImageRepository;
import com.opms.repositories.TeacherRepository;
import com.opms.repositories.UserDataRepository;
import com.opms.services.TeacherService;
import com.opms.utils.FileUtil;

@Service
public class TeacherServiceImpl extends TeacherMapper implements TeacherService{

	private final TeacherRepository teacherRepository;
	private final UserDataRepository userDataRepository;
	private final PasswordEncoder passwordEncoder;
	private final CourseRepository courseRepository;
	private final ImageRepository imageRepository;
	private final AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
    private String bucketName;
	
	public TeacherServiceImpl(ModelMapper modelMapper , 
			TeacherRepository teacherRepository ,
			UserDataRepository userDataRepository , 
			CourseRepository courseRepository,
			ImageRepository imageRepository,
			PasswordEncoder passwordEncoder,
			AmazonS3 s3Client) {
		super(modelMapper);
		this.teacherRepository = teacherRepository;
		this.userDataRepository = userDataRepository;
		this.passwordEncoder = passwordEncoder;
		this.imageRepository = imageRepository;
		this.courseRepository = courseRepository;
		this.s3Client = s3Client;
	}

	@Override
	public TeacherDto create(TeacherDto dto) {
		Teacher teacher = toEntity(dto);
		teacher.setCreatedOn(LocalDateTime.now());
		teacher.setIsActivated(Boolean.FALSE);
		teacher.setStatus(SignupStatus.NEW);
		
		UserData uData = new UserData();
		uData.setCity(dto.getUserData().getCity());
		uData.setCountry(dto.getUserData().getCountry());
		uData.setProvince(dto.getUserData().getProvince());
		uData.setZipCode(dto.getUserData().getZipCode());
		
		UserData data2 = userDataRepository.save(uData);
		teacher.setPassword( passwordEncoder.encode(dto.getPassword()) );
		teacher.setUserRole(dto.getUserRole());
		teacher.setUserData(data2);
		
		return toDto(teacherRepository.save(teacher));
	}

	@Override
	public TeacherDto findUser(String username) {
		return toDto(teacherRepository.findByUsername(username));
	}

	@Override
	public TeacherDto update(MultipartFile file , TeacherDto teacherDto) {
		Teacher user = teacherRepository.findById(teacherDto.getId()).get();
		String email = null;
		String password = null;
		if(teacherDto.getEmail() == null && teacherDto.getPassword() == null) {
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
				image.setTeacher(user);
				
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
		data.setProvince(teacherDto.getUserData().getProvince());
		data.setCity(teacherDto.getUserData().getCity());
		data.setCountry(teacherDto.getUserData().getCountry());
		data.setZipCode(teacherDto.getUserData().getZipCode());
		
		userDataRepository.save(data);
		
		user.setEmail(email);
		user.setPassword(password);
		user.setAddress(teacherDto.getAddress());
		user.setFirstName(teacherDto.getFirstName());
		user.setMiddleName(teacherDto.getMiddleName());
		user.setLastName(teacherDto.getLastName());
		
		
		return this.toDto( teacherRepository.save(user) );
	}

	@Override
	public Boolean verifyPassword(String password, Long userId) {
		return passwordEncoder.matches(password, teacherRepository.findById(userId).get().getPassword());
	}

	@Override
	public TeacherDto updateCredentials(TeacherDto dto) {
		Teacher user = teacherRepository.findById(dto.getId()).get();
		UserData data = userDataRepository.findById(user.getUserData().getId()).get();
		user.setUserData(data);
		
		if( !passwordEncoder.matches(dto.getPassword() , user.getPassword() )){
			user.setPassword(passwordEncoder.encode( dto.getPassword()));
		}
		return this.toDto( teacherRepository.save(user) );
	}

	@Override
	public TeacherDto getById(Long id) {
		return toDto(teacherRepository.findById(id).orElseThrow(null));
	}

	@Override
	public Page<TeacherDto> filterSearch(String status, String createdOn, String keyword, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<TeacherDto> list = toDtoList( teacherRepository.findAllWithPaging(keyword, createdOn , status , offset, pageable.getPageSize()) );
		int totalSize = teacherRepository.totalSize();
		return new PageImpl<>(list , pageable , totalSize );
	}

	@Override
	public Page<TeacherDto> findAllPageable(Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<TeacherDto> list = toDtoList(teacherRepository.findAllPageable(offset, pageable.getPageSize() ));
		return new PageImpl<>(list , pageable , list.size() );
	}

	@Override
	public TeacherDto update(Long id, String status) {
		Teacher teacher = teacherRepository.findById(id).get();
		if(teacher == null ) {
			return null;
		}else {
			teacher.setStatus(SignupStatus.valueOf(status));
			if(SignupStatus.valueOf(status) == SignupStatus.APPROVED) {
				teacher.setIsActivated(true);
			}else {
				teacher.setIsActivated(false);
			}
			return toDto ( teacherRepository.save(teacher));
		}
	}
}
