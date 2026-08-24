package com.springboot.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.validator.CreateGroup;
import com.springboot.scm.validator.UpdateGroup;
import com.springboot.scm.validator.ValidImage;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {
@NotBlank(message = "Name is required",groups = {CreateGroup.class, UpdateGroup.class})	
private String name;

@NotBlank(message = "Email is required",groups = {CreateGroup.class, UpdateGroup.class})
@Email(message="Invalid Email Example @gmail.com")
private String email;

@NotBlank(message="Phone number is required",groups = {CreateGroup.class, UpdateGroup.class})
@Pattern(
	    regexp="^(\\+91|0)?[6-9][0-9]{9}$",
	    message="Invalid Indian Phone Number"
	)
private String phoneNumber;

private String description;

@NotBlank(message="Address is required",groups = {CreateGroup.class, UpdateGroup.class})
private String address;

private String websiteLink;

private String linkedInLink;

private boolean favorite;

//Annotation create karenge jo file validate kare
//size , resolution

@ValidImage(message="Address is required",groups = { CreateGroup.class})
private MultipartFile contactImage;

private String picture;



}
