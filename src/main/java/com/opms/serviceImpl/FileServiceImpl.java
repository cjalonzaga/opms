package com.opms.serviceImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.opms.db.dtos.UserFileDto;
import com.opms.db.entities.Folder;
import com.opms.db.entities.Student;
import com.opms.db.entities.UserFile;
import com.opms.db.mappers.UserFileMapper;
import com.opms.repositories.FolderRepository;
import com.opms.repositories.StudentRepository;
import com.opms.repositories.UserFileRepository;
import com.opms.services.FileService;
import com.opms.utils.FileUtil;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class FileServiceImpl extends UserFileMapper implements FileService{
	
	private final StudentRepository studentRepository;
	private final FolderRepository folderRepository;
	private final UserFileRepository userFileRepository;
	private final AmazonS3 s3Client;
	
	private final Path root = Paths.get("uploads");
	
	@Value("${application.bucket.name}")
    private String bucketName;
	
	public FileServiceImpl(ModelMapper modelMapper , 
			StudentRepository studentRepository,
			UserFileRepository userFileRepository,
			AmazonS3 s3Client,
			FolderRepository folderRepository) {
		super(modelMapper);
		this.studentRepository = studentRepository;
		this.userFileRepository = userFileRepository;
		this.s3Client = s3Client;
		this.folderRepository = folderRepository;
	}

	@Override
	public UserFileDto upload(UserFileDto dto , Long userId , MultipartFile file) {
		if(dto == null && file.isEmpty()) {
			return null;
		}
		
		UserFile entity = toEntity(dto);
		Folder folder = folderRepository.findById(dto.getFolderId()).get();
		Student s = studentRepository.findById(userId).get();
		entity.setFolder(folder);
		entity.setStudent(s);
		entity.setOriginalFileName(file.getOriginalFilename());
		entity.setCreatedOn(LocalDateTime.now());
		entity.setUpdatedOn(LocalDateTime.now());
		if(!file.isEmpty()) {
			File fileObject = FileUtil.convertMultiPartFileToFile(file);
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
			String uri = s3Client.getUrl(bucketName, fileName).toString();
			
			entity.setFileName(fileName);
			entity.setType(FileUtil.getFileType(fileName));
			entity.setUri(uri);
			
			fileObject.delete();
		}
		
		return toDto(userFileRepository.save(entity));
	}

	@Override
	public Page<UserFileDto> filterSearch(String status, String createdOn, String keyword, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<UserFileDto> findAllPageableByUser(Pageable pageable ,Long userId) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<UserFileDto> list = toDtoList( userFileRepository.findAllPageableByUser(offset, pageable.getPageSize() , userId ) );
		return new PageImpl<>(list , pageable , list.size() );
	}

	@Override
	public void delete(Long fileId) {
		UserFile file = userFileRepository.findById(fileId).get();
		file.setIsValid(Boolean.FALSE);
		userFileRepository.save(file);
	}

	@Override
	public Page<UserFileDto> findAllPageable(Pageable pageable, Long folderId) {
		int offset = pageable.getPageNumber() * pageable.getPageSize();
		List<UserFileDto> list = toDtoList( userFileRepository.findAllPageable(offset, pageable.getPageSize() , folderId));
		return new PageImpl<>(list , pageable , list.size() );
	}

	@Override
	public UserFileDto uploadLocal(UserFileDto dto, MultipartFile file) {
		
		UserFile ufile = FileUtil.saveFile(file);
		
		return toDto(userFileRepository.save(ufile));
	}

	@Override
	public void dowloadFolderFiles(Long folderId , HttpServletResponse response) throws IOException {
		List<UserFile> fileList = userFileRepository.findAllByFolder(folderId);
		Folder folder = folderRepository.findById(folderId).get();
		
		String zipname = folder.getName();
		
		ServletOutputStream sos = response.getOutputStream();
		ZipOutputStream zos = new ZipOutputStream(sos);
		
	    try {
	    	
	        ZipEntry zipentry = null;
	        byte bytes[] = new byte[4096];
	        response.setContentType("application/zip");
	        response.setHeader("Content-Disposition", "attachment; filename="+zipname+".zip");

	        for (UserFile file : fileList) {

	            zipentry = new ZipEntry(file.getFileName());
	            zos.putNextEntry(zipentry);
	            S3ObjectInputStream in = this.downloadFileAsStream(bucketName, file.getFileName());
	            int bytesRead = -1;

	            while ((bytesRead = in.read(bytes)) != -1) {
	                zos.write(bytes, 0, bytesRead);
	            }
	            
	            in.close();
	        }
	    } catch (Exception e){
	        e.printStackTrace();
	    } finally {
	        zos.flush();
	        zos.closeEntry();
	        zos.close();
	        sos.close();
	    }
	    response.flushBuffer();
	}
	
	private S3ObjectInputStream downloadFileAsStream(String bucketName, String objectKey) {

	    try {
	        GetObjectRequest s3ObjectReq = new GetObjectRequest(bucketName, objectKey);
	        long startTime = System.currentTimeMillis();
	        S3Object downlodedObjectMD = s3Client.getObject(s3ObjectReq);
	        return downlodedObjectMD.getObjectContent();

	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

	@Override
	public UserFileDto directUpload(Long userId, MultipartFile file) {
		if(file.isEmpty()) {
			return null;
		}
		
		UserFile entity = new UserFile();
		Student s = studentRepository.findById(userId).get();
		entity.setStudent(s);
		entity.setOriginalFileName(file.getOriginalFilename());
		entity.setCreatedOn(LocalDateTime.now());
		entity.setUpdatedOn(LocalDateTime.now());
		
		if(!file.isEmpty()) {
			File fileObject = FileUtil.convertMultiPartFileToFile(file);
			String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
			String uri = s3Client.getUrl(bucketName, fileName).toString();
			
			entity.setFileName(fileName);
			entity.setType(FileUtil.getFileType(fileName));
			entity.setUri(uri);
			
			fileObject.delete();
		}
		
		return toDto(userFileRepository.save(entity));
	}
}
