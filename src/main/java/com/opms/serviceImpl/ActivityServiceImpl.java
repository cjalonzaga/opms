package com.opms.serviceImpl;

import java.io.File;
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
import com.opms.db.dtos.ActivityDto;
import com.opms.db.entities.Activity;
import com.opms.db.entities.Section;
import com.opms.db.entities.Subject;
import com.opms.db.entities.Teacher;
import com.opms.db.entities.UserFile;
import com.opms.db.mappers.ActivityMapper;
import com.opms.repositories.ActivityRepository;
import com.opms.repositories.SectionRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.SubjectRepository;
import com.opms.repositories.TeacherRepository;
import com.opms.repositories.UserFileRepository;
import com.opms.services.ActivityService;
import com.opms.utils.FileUtil;
import com.opms.utils.PaginationUtil;

@Service
public class ActivityServiceImpl extends ActivityMapper implements ActivityService{
	
	private final ActivityRepository activityRepository;
	private final TeacherRepository teacherRepository;
	private final SubjectRepository subjectRepository;
	private final UserFileRepository userFileRepository;
	private final SectionRepository sectionRepository;
	private final AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
    private String bucketName;

	public ActivityServiceImpl(ModelMapper modelMapper , 
			ActivityRepository activityRepository,
			TeacherRepository teacherRepository,
			SubjectRepository subjectRepository,
			AmazonS3 s3Client , 
			UserFileRepository userFileRepository,
			SectionRepository sectionRepository) {
		super(modelMapper);
		this.activityRepository = activityRepository;
		this.teacherRepository = teacherRepository;
		this.subjectRepository = subjectRepository;
		this.s3Client = s3Client;
		this.userFileRepository = userFileRepository;
		this.sectionRepository = sectionRepository;
	}

	@Override
	public ActivityDto get(Long id) {
		
		return toDto( activityRepository.findById(id).get() );
	}

	@Override
	public List<ActivityDto> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ActivityDto create(ActivityDto d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ActivityDto> findAllActivityByUserPaging(Long userId , PaginationUtil pagination) {
		
//		int offset = (pagination.getOffSet() != null ) ? pagination.getOffSet() * pagination.getLimit() : 0 * 3;
//		int limit = (pagination.getLimit() != null ) ? pagination.getLimit() : 3;
//		
//		List<Activity> activities = activityRepository.findAllActivityByUserPaging(userId, offset, limit);
		
		return null;
	}

	@Override
	public ActivityDto createByUser(ActivityDto dto, Long userId , MultipartFile file) {
		
		if( activityRepository.ifActivityExist(dto.getTitle()) ) {
			return null;
		}
		
		Activity activity = new Activity();
		activity.setDueDate(dto.getDueDate());
		activity.setInstruction(dto.getInstruction());
		activity.setTitle(dto.getTitle());
		activity.setTaskType(dto.getTaskType());
		
		Teacher teacher = teacherRepository.findById(userId).get();
		activity.setTeacher(teacher);
		Subject subject = subjectRepository.findById(dto.getSubjectId()).get();
		activity.setSubject(subject);
		
		List<Section> sections = sectionRepository.findAllById(dto.getSectionIds());
		
		activity.setSections(sections);
		
		UserFile ufile = null;
		Activity saved = null;
		
		if(!file.isEmpty()) {
			File fileObject = FileUtil.convertMultiPartFileToFile(file);
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
			String uri = s3Client.getUrl(bucketName, fileName).toString();
			
			ufile = new UserFile();
			ufile.setOriginalFileName(file.getOriginalFilename());
			ufile.setFileName(fileName);
			ufile.setUri(uri);
			
			saved = activityRepository.save(activity);
			
			ufile.setActivity(saved);
			userFileRepository.save(ufile);
			
			activity.setFiles(List.of(ufile));
			
			fileObject.delete();
		}else {
			saved = activityRepository.save(activity);
		}
		
		return toDto(saved);
	}
	
	@Override
	public ActivityDto update(ActivityDto dto, Long userId, MultipartFile file) {
		
		Activity activity = activityRepository.findById(dto.getId()).get();
		activity.setDueDate(dto.getDueDate());
		activity.setInstruction(dto.getInstruction());
		activity.setTitle(dto.getTitle());
		activity.setTaskType(dto.getTaskType());
		
		Subject subject = subjectRepository.findById(dto.getSubjectId()).get();
		activity.setSubject(subject);
		
		List<Section> sections = sectionRepository.findAllById(dto.getSectionIds());
		
		activity.setSections(sections);
		
		UserFile ufile = null;
		Activity saved = null;
		
		if(!file.isEmpty()) {
			File fileObject = FileUtil.convertMultiPartFileToFile(file);
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
			String uri = s3Client.getUrl(bucketName, fileName).toString();
			
			ufile = new UserFile();
			ufile.setOriginalFileName(file.getOriginalFilename());
			ufile.setFileName(fileName);
			ufile.setUri(uri);
			
			saved = activityRepository.save(activity);
			
			ufile.setActivity(saved);
			userFileRepository.save(ufile);
			
			activity.setFiles(List.of(ufile));
			
			fileObject.delete();
		}else {
			saved = activityRepository.save(activity);
		}
		
		return toDto(saved);
	}

	@Override
	public Page<ActivityDto> findAllByUserWithPaging(Long userId, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<ActivityDto> activityList = toDtoList( activityRepository.findAllActivitiesByUserPaging(userId, offset, pageable.getPageSize() ) );
		int totalSize = activityRepository.totalSize(userId);
		return new PageImpl<>(activityList , pageable , totalSize);
	}

	@Override
	public Page<ActivityDto> searchAllByUser(Long userId, String createdOn, String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ActivityDto> findActivitiesByStudent(Long studentId) {
		List<Activity> activities = activityRepository.findAllByStudent(studentId);
		return toDtoList(activities);
	}

}
