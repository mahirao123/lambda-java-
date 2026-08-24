package com.springboot.scm.validator;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.helpers.AppConstants;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageFileValidator implements ConstraintValidator <ValidImage,MultipartFile>{

	
	
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {

	    if (file == null || file.isEmpty()) {
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("File can't be empty")
	               .addConstraintViolation();
	        return false;
	    }

	    if (file.getSize() >AppConstants. MAX_FILE_SIZE) {
	        context.disableDefaultConstraintViolation();
	        context.buildConstraintViolationWithTemplate("File size should be 5 MB")
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
