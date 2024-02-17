package com.opms.services;

import org.springframework.web.multipart.MultipartFile;

import com.opms.db.dtos.ImageDto;
import com.opms.db.dtos.UserDto;

public interface ImageService{
	ImageDto upload(MultipartFile file , UserDto userDto);
}
