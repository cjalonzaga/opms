package com.opms.serviceImpl;

import java.util.List;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.StudentDto;
import com.opms.db.entities.Course;
import com.opms.db.entities.Section;
import com.opms.db.entities.Student;
import com.opms.db.entities.UserData;
import com.opms.db.mappers.StudentMapper;
import com.opms.repositories.CourseRepository;
import com.opms.repositories.SectionRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.UserDataRepository;
import com.opms.services.StudentService;

@Service
public class StudentServiceImpl extends StudentMapper implements StudentService{

	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;
	private final SectionRepository sectionRepository;
	private final UserDataRepository userDataRepository;
	private final PasswordEncoder passwordEncoder;
	
	public StudentServiceImpl(ModelMapper modelMapper , 
			StudentRepository studentRepository , 
			CourseRepository courseRepository , 
			SectionRepository sectionRepository,
			UserDataRepository userDataRepository ,
			PasswordEncoder passwordEncoder) {
		super(modelMapper);
		this.studentRepository = studentRepository;
		this.courseRepository = courseRepository;
		this.sectionRepository = sectionRepository;
		this.userDataRepository = userDataRepository;
		this.passwordEncoder = passwordEncoder;
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
}
