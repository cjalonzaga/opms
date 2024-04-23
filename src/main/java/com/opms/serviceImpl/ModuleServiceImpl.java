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
import com.opms.db.dtos.ModulesDto;
import com.opms.db.entities.ModuleFile;
import com.opms.db.entities.Modules;
import com.opms.db.entities.Section;
import com.opms.db.entities.Subject;
import com.opms.db.entities.UserFile;
import com.opms.db.mappers.ModuleMapper;
import com.opms.repositories.ModuleFileRepository;
import com.opms.repositories.ModuleRepository;
import com.opms.repositories.SectionRepository;
import com.opms.repositories.SubjectRepository;
import com.opms.repositories.TeacherRepository;
import com.opms.services.ModuleService;
import com.opms.utils.FileUtil;

@Service
public class ModuleServiceImpl extends ModuleMapper implements ModuleService{

	private final ModuleRepository moduleRepository;
	private final AmazonS3 s3Client;
	private final ModuleFileRepository moduleFileRepository;
	private final SectionRepository sectionRepository;
	private final SubjectRepository subjectRepository;
	private final TeacherRepository teacherRepository;
	
	@Value("${application.bucket.name}")
    private String bucketName;
	
	public ModuleServiceImpl(ModelMapper modelMapper , ModuleRepository moduleRepository, 
			AmazonS3 s3Client , ModuleFileRepository moduleFileRepository , 
			SectionRepository sectionRepository , SubjectRepository subjectRepository , 
			TeacherRepository teacherRepository) {
		super(modelMapper);
		this.moduleRepository = moduleRepository;
		this.s3Client = s3Client;
		this.moduleFileRepository = moduleFileRepository;
		this.sectionRepository = sectionRepository;
		this.subjectRepository = subjectRepository;
		this.teacherRepository = teacherRepository;
	}

	@Override
	public ModulesDto get(Long id) {
		if(id == null) {
			return null;
		}
		
		return toDto(moduleRepository.findById(id).get());
	}

	@Override
	public ModulesDto create(ModulesDto dto, MultipartFile file , Long teacherId) {
		Modules mod = new Modules();
		
		mod.setName(dto.getName());
		mod.setNote(dto.getNote());
		
		List<Section> sections = this.sectionRepository.findAllById(List.of( dto.getSectionId() ));
		
		Subject subject = subjectRepository.findById(dto.getSubjectId()).get();
		mod.setSubject(subject);
		mod.setSections(sections);
		
		mod.setTeacher(teacherRepository.findById(teacherId).get());
		
		Modules saved = null;
		
		if(!file.isEmpty()) {
			File fileObject = FileUtil.convertMultiPartFileToFile(file);
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
			String uri = s3Client.getUrl(bucketName, fileName).toString();
			
			ModuleFile mf = new ModuleFile();
			mf.setOriginalFileName(file.getOriginalFilename());
			mf.setFileName(fileName);
			mf.setUri(uri);
			mf.setType(FileUtil.getFileType(fileName));
			
			saved = moduleRepository.save(mod);
			
			moduleFileRepository.save(mf);
			
			fileObject.delete();
		}else {
			saved = moduleRepository.save(mod);
		}
		
		return toDto(saved);
	}

	@Override
	public Page<ModulesDto> findAllByUserWithPaging(Long userId, Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<ModulesDto> activityList = toDtoList( moduleRepository.findAllModulesByUserPaging(userId, offset, pageable.getPageSize() ) );
		int totalSize = moduleRepository.totalSize(userId);
		return new PageImpl<>(activityList , pageable , totalSize);
	}

	@Override
	public Page<ModulesDto> searchAllByUser(Long userId, String createdOn, String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		moduleRepository.deleteById(id);
	}

	@Override
	public Page<ModulesDto> findByStudentSection(Long sectionId , Pageable pageable) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<ModulesDto> activityList = toDtoList( moduleRepository.findAllByStudentSection(sectionId , offset, pageable.getPageSize()) );
		int totalSize = moduleRepository.totalSizeBySection(sectionId);
		return new PageImpl<>(activityList , pageable , totalSize);
	}
	
}
