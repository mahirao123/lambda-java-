package com.springboot.scm.validator;
import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Constraint(validatedBy=FileValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFile {
	
	String message() default "File is required";
	
	Class <?> [] groups() default {};
	
	Class<? extends Payload> [] payload() default{};

}
