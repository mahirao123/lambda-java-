package com.springboot.scm.validator;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator <ValidFile,MultipartFile>{

	private static final long MAX_FILE_SIZE = 1024* 1024* 2; //2MB
	
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

	    if (file == null || file.isEmpty()) {
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("File can't be empty")
	               .addConstraintViolation();
	        return false;
	    }

	    if (file.getSize() > MAX_FILE_SIZE) {
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("File size should be 2 MB")
	               .addConstraintViolation();
	        return false;
	    }

	    String contentType = file.getContentType();

	    if (contentType == null ||
	        (!contentType.equals("image/jpeg") &&
	         !contentType.equals("image/png") &&
	         !contentType.equals("image/jpg"))) {

	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("Only JPG, JPEG, PNG allowed")
	               .addConstraintViolation();
	        return false;
	    }

	    return true;
	}
} 
