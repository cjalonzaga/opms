package com.opms.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.opms.db.dtos.SubjectDto;
import com.opms.db.entities.Course;
import com.opms.db.entities.Subject;
import com.opms.db.entities.Teacher;
import com.opms.db.entities.User;
import com.opms.db.mappers.SubjectMapper;
import com.opms.repositories.CourseRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.SubjectRepository;
import com.opms.repositories.TeacherRepository;
import com.opms.services.SubjectService;

@Service
public class SubjectServiceImpl extends SubjectMapper implements SubjectService{
	
	private final SubjectRepository subjectRepository;
	private final CourseRepository courseRepository;
	private final StudentRepository studentRepository;
	private final TeacherRepository teacherRepository;

	public SubjectServiceImpl(ModelMapper modelMapper , 
			SubjectRepository subjectRepository, 
			CourseRepository courseRepository,
			StudentRepository studentRepository,
			TeacherRepository teacherRepository) {
		super(modelMapper);
		this.subjectRepository = subjectRepository;
		this.courseRepository = courseRepository;
		this.studentRepository = studentRepository;
		this.teacherRepository = teacherRepository;
	}

	@Override
	public SubjectDto get(Long id) {
		return this.toDto(subjectRepository.findById(id).get());
	}

	@Override
	public List<SubjectDto> getAll() {
		return toDtoList(subjectRepository.findAll());
	}

	@Override
	public SubjectDto create(SubjectDto d) {
		Subject subject = this.toEntity(d);
		Course course = courseRepository.findById(d.getCourseId()).get();
		subject.setCourse(course);
		subject.setCreatedOn(LocalDateTime.now());
		
		if(subjectRepository.ifSubjectExist( d.getCode() , course.getTeacher().getId() )) {
			return null;
		}
		
		return this.toDto(subjectRepository.save(subject));
	}

	@Override
	public void delete(Long id) {
		Subject sub = subjectRepository.findById(id).get();
		sub.setIsValid(Boolean.FALSE);
		subjectRepository.save(sub);
	}

	@Override
	public List<SubjectDto> findAllByUser(Long userId) {
		return toDtoList( subjectRepository.findAllByUser(userId) );
	}

	@Override
	public SubjectDto update(SubjectDto dto) {
		
		Subject subject = subjectRepository.findById(dto.getId()).get();
		subject.setCode(dto.getCode());
		Course course = courseRepository.findById(dto.getCourseId()).get();
		subject.setCourse(course);
		subject.setUpdatedOn(LocalDateTime.now());
		subject.setCourseLevel(dto.getCourseLevel());
		subject.setDescription(dto.getDescription());
		subject.setSemester(dto.getSemester());
		
		return toDto(subjectRepository.save(subject));
	}

	@Override
	public Page<SubjectDto> findAllByUserWithPaging(Long userId, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<SubjectDto> subjectList = toDtoList(subjectRepository.findAllByUserWithPaging(userId, offset, pageable.getPageSize() ));
		int totalSize = subjectRepository.totalSize(userId);
		
		return new PageImpl<>(subjectList , pageable , totalSize);
	}

	@Override
	public Page<SubjectDto> searchAllByUser(Long userId, String createdOn, String keyword, String courseLevel , String sem , Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		
		List<SubjectDto> subjectList = toDtoList(
					subjectRepository.searchAllByUser(userId , createdOn , keyword , courseLevel , sem , offset , pageable.getPageSize() ));
		
		return new PageImpl<>(subjectList , pageable , subjectList.size());
	}

	@Override
	public List<SubjectDto> searchAllByStudent(Long userId) {
		List<Course> courses = studentRepository.findById(userId).get().getCourse();
		
		return toDtoList( subjectRepository.searchAllByStudents(courses.get(0).getId()) );
	}

	@Override
	public Page<SubjectDto> findAll(Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<SubjectDto> subjectList = toDtoList(subjectRepository.findAll(offset, pageable.getPageSize() ));
		int totalSize = (int) subjectRepository.count();
		
		return new PageImpl<>(subjectList , pageable , totalSize);
	}

	@Override
	public Page<SubjectDto> searchAll(String createdOn, String keyword, String courseLevel, String sem,
			Pageable pageable) {
			int offset = pageable.getPageNumber() * pageable.getPageSize();
		
		List<SubjectDto> subjectList = toDtoList(
					subjectRepository.searchAll(createdOn , keyword , courseLevel , sem , offset , pageable.getPageSize() ));
		
		return new PageImpl<>(subjectList , pageable , subjectList.size());
	}

	@Override
	public SubjectDto createByUser(SubjectDto dto, Long userId) {
		Subject subject = this.toEntity(dto);
		Course course = courseRepository.findById(dto.getCourseId()).get();
		subject.setCourse(course);
		subject.setCreatedOn(LocalDateTime.now());
		subject.setTeacher(teacherRepository.findById(userId).get());
		if(subjectRepository.ifSubjectExist( dto.getCode() , userId )) {
			return null;
		}
		return this.toDto(subjectRepository.save(subject));
	}

}
