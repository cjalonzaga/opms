package com.opms.serviceImpl;

import java.io.File;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.opms.db.dtos.ImageDto;
import com.opms.db.dtos.UserDto;
import com.opms.db.entities.Image;
import com.opms.db.mappers.ImageMapper;
import com.opms.repositories.ImageRepository;
import com.opms.services.ImageService;
import com.opms.utils.FileUtil;

@Service
public class ImageServiceImpl extends ImageMapper implements ImageService{
	
	private final ImageRepository imageRepository;
	private final AmazonS3 s3Client;
	
	@Value("${application.bucket.name}")
    private String bucketName;
	
	public ImageServiceImpl(ModelMapper modelMapper , ImageRepository imageRepository , AmazonS3 s3Client ) {
		super(modelMapper);
		this.imageRepository = imageRepository;
		this.s3Client = s3Client;
	}

	@Override
	public ImageDto upload(MultipartFile file , UserDto userDto) {
		File fileObject = FileUtil.convertMultiPartFileToFile(file);
		String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
		s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObject));
		String uri = s3Client.getUrl(bucketName, fileName).toString();
		
		Image image = new Image();
		image.setFileName(fileName);
		image.setUri(uri);
		
		fileObject.delete();
		
		return this.toDto( imageRepository.save(image) );
	}
}
