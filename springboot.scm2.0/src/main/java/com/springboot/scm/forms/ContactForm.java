package com.springboot.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.validator.ValidFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactForm {

@NotBlank(message="Name is required")	
private String name;

@NotBlank(message="Email is required")
@Email(message="Invalid Email Example @gmail.com")
private String email;

@NotBlank(message="Phone number is required")
@Pattern(regexp="^[0-9]{10}$",message="Invalid Phone Number")
private String phoneNumber;

private String description;

@NotBlank(message="Address is required")
private String address;

private String websiteLink;

private String linkedInLink;

private boolean favorite;

//Annotation create karenge jo file validate kare
//size , resolution

@ValidFile(message="Invalid File")
private MultipartFile contactImage;



}
