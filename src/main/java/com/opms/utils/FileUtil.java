package com.opms.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import com.opms.enums.FileTypes;

public class FileUtil {
	
	private String[] fileTypes = {"pdf" , "docx" , "txt" , "xlsx" , "pptx"}; 
	
	public static File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return convertedFile;
    }
	
	public static FileTypes getFileType(String fileName) {
		String ext = getExtensionByStringHandling(fileName).get();
		FileTypes types = null;
		
		if(ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png") || ext.equalsIgnoreCase("jpg")){
			types = FileTypes.IMAGE;
		}else if(ext.equalsIgnoreCase("xlsx") || ext.equalsIgnoreCase("xls")) {
			types = FileTypes.EXCEL;
		}else if(ext.equalsIgnoreCase("docx")) {
			types = FileTypes.WORD;
		}else if(ext.equalsIgnoreCase("txt")) {
			types = FileTypes.TEXT;
		}else if(ext.equalsIgnoreCase("pptx")){
			types = FileTypes.PPT;
		}else if(ext.equalsIgnoreCase("pdf")) {
			types = FileTypes.PDF;
		}
		
		return types;
	}
	
	private static Optional<String> getExtensionByStringHandling(String filename) {
		return Optional.ofNullable(filename)
    	      .filter(f -> f.contains("."))
    	      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
	}
}
