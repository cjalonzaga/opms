package com.opms.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.SectionDto;
import com.opms.db.entities.Course;
import com.opms.db.entities.Section;
import com.opms.db.entities.Teacher;
import com.opms.db.mappers.SectionMapper;
import com.opms.repositories.CourseRepository;
import com.opms.repositories.SectionRepository;
import com.opms.repositories.TeacherRepository;
import com.opms.services.SectionService;

@Service
public class SectionServiceImpl extends SectionMapper implements SectionService{

	private final SectionRepository sectionRepository;
	private final TeacherRepository teacherRepository;
	private final CourseRepository courseRepository;
	
	public SectionServiceImpl(ModelMapper modelMapper , 
			TeacherRepository teacherRepository , 
			SectionRepository sectionRepository , 
			CourseRepository courseRepository) {
		super(modelMapper);
		this.sectionRepository = sectionRepository;
		this.teacherRepository = teacherRepository;
		this.courseRepository = courseRepository;
	}

	@Override
	public SectionDto create(SectionDto dto, Long userId) {
		Section section = new Section();
		section.setName(dto.getName());
		section.setCourseLevel(dto.getCourseLevel());
		Teacher teacher = teacherRepository.findById(userId).get();
		section.setTeacher(teacher);
		
		List<Course> courses = courseRepository.findAllById(List.of(dto.getCourseId()));
		
		section.setCourses(courses);
		
		return toDto( sectionRepository.save(section) );
	}

	@Override
	public List<SectionDto> findAllByTeacher(Long userId) {
		return toDtoList(sectionRepository.findAllByTeacher(userId));
	}

	@Override
	public SectionDto get(Long id) {
		return toDto( sectionRepository.findById(id).get() );
	}

	@Override
	public List<SectionDto> getAll() {
		return toDtoList(sectionRepository.findAll());
	}

	@Override
	public SectionDto update(SectionDto dto, Long userId) {
		
		Section section = sectionRepository.findById(dto.getId()).get();
		section.setName(dto.getName());
		section.setCourseLevel(dto.getCourseLevel());
		section.setUpdatedOn(LocalDateTime.now());
		
		Teacher teacher = teacherRepository.findById(userId).get();
		section.setTeacher(teacher);
		
		
		List<Course> courses = courseRepository.findAllById(List.of(dto.getCourseId()));
		
		section.setCourses(courses);
		
		
		return toDto( sectionRepository.save(section) );
	}
	
	

}
