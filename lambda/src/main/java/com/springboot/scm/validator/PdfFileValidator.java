package com.springboot.scm.validator;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PdfFileValidator
        implements ConstraintValidator<ValidPdf, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile file,
                           ConstraintValidatorContext context) {

        // Check null or empty file
        if (file == null || file.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "PDF file is required")
                    .addConstraintViolation();

            return false;
        }

        // Check content type
        String contentType = file.getContentType();

        if (contentType == null ||
                !contentType.equalsIgnoreCase("application/pdf")) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "Only PDF files are allowed")
                    .addConstraintViolation();

            return false;
        }

        // Check file extension
        String fileName = file.getOriginalFilename();

        if (fileName == null ||
                !fileName.toLowerCase().endsWith(".pdf")) {

            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                    "File must be in PDF format")
                    .addConstraintViolation();

            return false;
        }

        return true;
    }
}