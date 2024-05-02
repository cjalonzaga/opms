package com.opms.serviceImpl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.opms.db.entities.ModuleFile;
import com.opms.repositories.ModuleFileRepository;
import com.opms.services.ModuleFileService;

@Service
public class ModuleFileServiceImpl implements ModuleFileService{
	
	private final ModuleFileRepository moduleFileRepository;
	private final AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
    private String bucketName;
	
	public ModuleFileServiceImpl(ModuleFileRepository moduleFileRepository , AmazonS3 s3Client){
		this.moduleFileRepository = moduleFileRepository;
		this.s3Client = s3Client;
	}

	@Override
	public String delete(Long id) {
		ModuleFile file = moduleFileRepository.findById(id).get();
		if(file == null) {
			return null;
		}
		
		try {
			s3Client.deleteObject(bucketName, file.getFileName());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		moduleFileRepository.deleteById(id);
		
		return "DELETE";
	}

}
