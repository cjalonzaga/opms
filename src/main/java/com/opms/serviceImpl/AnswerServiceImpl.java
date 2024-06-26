package com.opms.serviceImpl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.opms.db.dtos.AnswerDto;
import com.opms.db.entities.Activity;
import com.opms.db.entities.Answer;
import com.opms.db.entities.Section;
import com.opms.db.entities.Student;
import com.opms.db.entities.UserFile;
import com.opms.db.mappers.AnswerMapper;
import com.opms.enums.AnswerStatus;
import com.opms.repositories.ActivityRepository;
import com.opms.repositories.AnswerRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.UserFileRepository;
import com.opms.services.AnswerService;
import com.opms.utils.FileUtil;

@Service
public class AnswerServiceImpl extends AnswerMapper implements AnswerService{

	private final ActivityRepository activityRepository;
	private final AmazonS3 s3Client;
	private final StudentRepository studentRepository;
	private final AnswerRepository answerRepository;
	private final UserFileRepository userFileRepository;
	
	@Value("${application.bucket.name}")
    private String bucketName;
	
	public AnswerServiceImpl(ModelMapper modelMapper , 
			ActivityRepository activityRepository, 
			AmazonS3 s3Client , 
			StudentRepository studentRepository ,
			AnswerRepository answerRepository,
			UserFileRepository userFileRepository) {
		super(modelMapper);
		this.activityRepository = activityRepository;
		this.s3Client = s3Client;
		this.studentRepository = studentRepository;
		this.answerRepository = answerRepository;
		this.userFileRepository = userFileRepository;
	}

	@Override
	public AnswerDto create(AnswerDto dto, MultipartFile file) {
		
		Answer answer = new Answer();
		answer.setFileUri(dto.getFileUri());
		
		
		Activity activity = null;
		if(dto.getActivityId() != null) {
			activity = activityRepository.findById(Long.valueOf( dto.getActivityId() ) ).get();
			answer.setActivity(activity);
			if (!activity.getDueDate().isBefore(LocalDateTime.now())){
				answer.setStatus(AnswerStatus.SUBMITTED);
			}else {
				answer.setStatus(AnswerStatus.LATE);
			}
		}
		
		Student student = null;
		
		if(dto.getUserId() != null) {
			student = studentRepository.findById(Long.valueOf(dto.getUserId())).get();
			answer.setStudent(student);
		}
		
		if(activity  != null && student != null) {
			List<Long> sectionIds = activity.getSections().stream().map( sect -> sect.getId() ).toList();
			List<Section> section = student.getSections().stream().filter( 
										sect -> sectionIds.contains(sect.getId())
									).toList();
			if(!section.isEmpty()) {
				answer.setSection(section.get(0));
			}
		}
		
		UserFile userFile = null;
		
		if(!file.isEmpty()) {
			File fileObject = FileUtil.convertMultiPartFileToFile(file);
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
			String uri = s3Client.getUrl(bucketName, fileName).toString();
			
			userFile = new UserFile();
			userFile.setOriginalFileName(file.getOriginalFilename());
			userFile.setFileName(fileName);
			userFile.setUri(uri);
			userFile.setStudent(student);
			userFile.setType(FileUtil.getFileType(fileName));
			
			answer = answerRepository.save(answer);
			
			userFile.setAnswer(answer);
			userFileRepository.save(userFile);
			
			fileObject.delete();
			
			return toDto(answer);
		}else {
			return toDto ( answerRepository.save(answer) );
		}
	}

	@Override
	public AnswerDto findByUser(Long userId, Long activityId) {
		Answer answer = answerRepository.findByUser(userId, activityId);
		if(answer == null) {
			return null;
		}
		return toDto(answer);
	}

	@Override
	public List<AnswerDto> findAllByActivity(Long activityId, Long sectionId) {
		return toDtoList(this.answerRepository.findAllByActivity(activityId));
	}

	@Override
	public List<AnswerDto> findAllBySection(Long activityId, Long sectionId) {
		return toDtoList(answerRepository.findAllBySection(activityId , sectionId));
	}

	@Override
	public AnswerDto updateAnswer(String status, Long id) {
		if(status == null && id == null) {
			return null;
		}
		Answer answer = answerRepository.findById(id).get();
		answer.setStatus(AnswerStatus.valueOf(status));
		
		return toDto(answerRepository.save(answer));
	}

}
