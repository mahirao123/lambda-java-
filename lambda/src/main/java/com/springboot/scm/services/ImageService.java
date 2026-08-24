package com.springboot.scm.services;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
	
	String uploadFile (MultipartFile Image, String filename);
	
	String getUrlFromPublicId(String publicId);
	
	void deleteCloudinaryFile(String cloudinaryId,String mediaType) throws IOException;


	

}
