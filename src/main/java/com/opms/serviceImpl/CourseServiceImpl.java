package com.opms.serviceImpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.CourseDto;
import com.opms.db.entities.Course;
import com.opms.db.entities.Section;
import com.opms.db.entities.Teacher;
import com.opms.db.mappers.CourseMapper;
import com.opms.enums.CouncilType;
import com.opms.repositories.CourseRepository;
import com.opms.repositories.SectionRepository;
import com.opms.repositories.TeacherRepository;
import com.opms.services.CourseService;

@Service
public class CourseServiceImpl extends CourseMapper implements CourseService{
	
	private final CourseRepository courseRepository;
	private final SectionRepository sectionRepository;
	private final TeacherRepository teacherRepository;

	public CourseServiceImpl(
			ModelMapper modelMapper , 
			CourseRepository courseRepository , 
			SectionRepository sectionRepository ,
			TeacherRepository teacherRepository) {
		super(modelMapper);
		this.courseRepository = courseRepository;
		this.sectionRepository = sectionRepository;
		this.teacherRepository = teacherRepository;
	}

	@Override
	public CourseDto get(Long id) {
		return this.toDto(courseRepository.findById(id).get());
	}

	@Override
	public List<CourseDto> getAll() {
		return this.toDtoList( courseRepository.findAll() );
	}

	@Override
	public CourseDto create(CourseDto d) {
		Course course = this.toEntity(d);
		//make validation
		return this.toDto(courseRepository.save(course));
	}

	@Override
	public void delete(Long id) {
		courseRepository.deleteById(id);
	}

	@Override
	public CourseDto createWithUser(CourseDto courseDto, Long userId) {
		Course course = this.toEntity(courseDto);
		if(courseRepository.ifCourseExist(courseDto.getAbbreviation() , userId)) {
			return null;
		}
		
		Teacher teacher = teacherRepository.findById(userId).get();
		course.setTeacher(teacher);
		Course saved = courseRepository.save(course);
		
		return this.toDto(saved);
	}

	@Override
	public CourseDto update(CourseDto courseDto) {
		Course course = courseRepository.findById(courseDto.getId()).get();
		course.setName(courseDto.getName());
		course.setAbbreviation(courseDto.getAbbreviation());
		course.setDescription(courseDto.getDescription());
		
		List<Section> sections = sectionRepository.findAllById(List.of(courseDto.getSectionId()));
		
		course.setSections(sections);
		
		return toDto(courseRepository.save(course));
	}

	@Override
	public List<CourseDto> findAllByUser(Long userId) {
		return toDtoList(courseRepository.findAllByUser(userId));
	}

	@Override
	public Page<CourseDto> findAllByUserWithPaging(Pageable pageable) {
		int offset =pageable.getPageNumber() * pageable.getPageSize();
		List<CourseDto> courseList = toDtoList( courseRepository.findAllCourseByUserPaging(offset , pageable.getPageSize()) );
		int totalSize = courseRepository.totalSize();
		return new PageImpl<>(courseList , pageable , totalSize);
	}

	@Override
	public Page<CourseDto> searchAllByUser(String createdOn, String keyword , Pageable pageable) {
		int offset =pageable.getPageNumber() * pageable.getPageSize();
		List<CourseDto> courseList = toDtoList(courseRepository.searchAllByUser(createdOn , keyword, offset, pageable.getPageSize() ));
		
		return new PageImpl<>(courseList , pageable , courseList.size() );
	}

	@Override
	public List<Course> findAllDistintByCouncil(CouncilType type) {
		// TODO Auto-generated method stub
		return null;
	}

}
