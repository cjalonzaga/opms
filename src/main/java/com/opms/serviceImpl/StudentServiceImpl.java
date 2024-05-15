package com.opms.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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
import com.opms.db.dtos.StudentDto;
import com.opms.db.entities.Course;
import com.opms.db.entities.Image;
import com.opms.db.entities.Section;
import com.opms.db.entities.Student;
import com.opms.db.entities.Teacher;
import com.opms.db.entities.UserData;
import com.opms.db.mappers.StudentMapper;
import com.opms.enums.SignupStatus;
import com.opms.repositories.CourseRepository;
import com.opms.repositories.ImageRepository;
import com.opms.repositories.SectionRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.UserDataRepository;
import com.opms.services.StudentService;
import com.opms.utils.FileUtil;

@Service
public class StudentServiceImpl extends StudentMapper implements StudentService{

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final SectionRepository sectionRepository;
	private final UserDataRepository userDataRepository;
	private final PasswordEncoder passwordEncoder;
	private final ImageRepository imageRepository;
	private final AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
    private String bucketName;
	
	public StudentServiceImpl(ModelMapper modelMapper , 
			StudentRepository studentRepository , 
			CourseRepository courseRepository , 
			SectionRepository sectionRepository,
			UserDataRepository userDataRepository ,
			PasswordEncoder passwordEncoder,
			 AmazonS3 s3Client,
			 ImageRepository imageRepository) {
		super(modelMapper);
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.sectionRepository = sectionRepository;
		this.userDataRepository = userDataRepository;
		this.passwordEncoder = passwordEncoder;
		this.s3Client = s3Client;
		this.imageRepository = imageRepository;
	}

	@Override
	public StudentDto findUser(String username) {
		return toDto( studentRepository.findByUsername(username) );
	}

	@Override
	public StudentDto create(StudentDto dto) {
		Student student = toEntity(dto);
		if(studentRepository.ifUserExist(dto.getUsername() , dto.getFirstName() , dto.getLastName())) {
			return null;
		}
		
		Course course = courseRepository.findById(dto.getCourseId()).get();
		student.setCourse(List.of(course));
		
		List<Section> section = sectionRepository.findAllById(List.of(dto.getSectionId()));
		student.setSections(section);
		student.setCreatedOn(LocalDateTime.now());
		student.setIsActivated(Boolean.FALSE);
		student.setStatus(SignupStatus.NEW);
		
		UserData uData = new UserData();
		uData.setCity(dto.getUserData().getCity());
		uData.setCountry(dto.getUserData().getCountry());
		uData.setProvince(dto.getUserData().getProvince());
		uData.setZipCode(dto.getUserData().getZipCode());
		
		student.setPassword( passwordEncoder.encode(dto.getPassword()) );
		
		UserData saved = userDataRepository.save(uData);
		student.setUserData(saved);
		
		return toDto( studentRepository.save(student) );
	}

	@Override
	public List<StudentDto> getAll() {
		return toDtoList( studentRepository.findAll() );
	}

	@Override
	public StudentDto getById(Long id) {
		return toDto( studentRepository.findById(id).get() );
	}

	@Override
	public Page<StudentDto> filterSearch(String status, String createdOn, String keyword, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<StudentDto> list = toDtoList( studentRepository.findAllWithPaging(keyword, createdOn , status , offset, pageable.getPageSize()) );
		int totalSize = studentRepository.totalSize();
		return new PageImpl<>(list , pageable , totalSize );
	}

	@Override
	public Page<StudentDto> findAllPageable(Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<StudentDto> list = toDtoList(studentRepository.findAllPageable(offset, pageable.getPageSize() ));
		return new PageImpl<>(list , pageable , list.size() );
	}

	@Override
	public StudentDto update(Long id , String status) {
		Student student = studentRepository.findById(id).get();
		if(student == null ) {
			return null;
		}else {
			student.setStatus(SignupStatus.valueOf(status));
			if(SignupStatus.valueOf(status) == SignupStatus.APPROVED) {
				student.setIsActivated(true);
			}else {
				student.setIsActivated(false);
			}
			return toDto ( studentRepository.save(student));
		}
	}

	@Override
	public StudentDto update(MultipartFile file, StudentDto teacherDto) {
		Student user = studentRepository.findById(teacherDto.getId()).get();
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
				image.setStudent(user);
				
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
		
		
		return this.toDto( studentRepository.save(user) );
	}

	@Override
	public StudentDto updateCredentials(StudentDto dto) {
		Student user = studentRepository.findById(dto.getId()).get();
		UserData data = userDataRepository.findById(user.getUserData().getId()).get();
		user.setUserData(data);
		
		if( !passwordEncoder.matches(dto.getPassword() , user.getPassword() )){
			user.setPassword(passwordEncoder.encode( dto.getPassword()));
		}
		return this.toDto( studentRepository.save(user) );
	}

	@Override
	public Boolean verifyPassword(String password, Long userId) {
		return passwordEncoder.matches(password, studentRepository.findById(userId).get().getPassword());
	}
	
	@Override
	public String delete(Long id) {
		if(id == null) {
			return "Failed to delete user.";
		}
		
		Student t = studentRepository.findById(id).get();
		t.setIsValid(false);
		t.setIsActivated(false);
		
		studentRepository.save(t);
		
		return "User successfully deleted.";
	}

	@Override
	public Integer countStudentBySection(Long sectionId) {
		return studentRepository.countStudent(sectionId);
	}
}
