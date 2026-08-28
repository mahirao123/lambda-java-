package com.springboot.scm.forms;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.validator.CreateGroup;
import com.springboot.scm.validator.UpdateGroup;
import com.springboot.scm.validator.ValidImage;
import com.springboot.scm.validator.ValidPdf;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeForm {

    @NotBlank(message = "Name is required",
            groups = {CreateGroup.class, UpdateGroup.class})
    @Size(min = 3, max = 50,
            message = "Name must be between 3 and 50 characters")
    @Pattern(
            regexp = "^[a-zA-Z ]+$",
            message = "Name must contain only alphabets"
    )
    private String name;

    @NotBlank(message = "Email is required",
            groups = {CreateGroup.class, UpdateGroup.class})
    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Phone number is required",
            groups = {CreateGroup.class, UpdateGroup.class})
    @Pattern(
            regexp = "^[6-9]\\d{9}$",
            message = "Enter a valid 10-digit Indian mobile number"
    )
    private String phoneNumber;

    
    @NotBlank(message = "Password is required",
            groups = {CreateGroup.class})
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,16}$",
            message = "Password must contain uppercase, lowercase, number, special character and be 8-16 characters long"
    )
    
     private String password;

    @NotBlank(message = "Role is required")
    private String role;

    // Joining Date
    @NotNull(message = "Joining date is required",
            groups = {CreateGroup.class})
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joiningDate;

    @ValidImage(message = "ProfilePic required",
            groups = {CreateGroup.class})
    private MultipartFile profilePic;

    @ValidImage(message = "BankAccount required",
            groups = {CreateGroup.class})
    private MultipartFile bankAccountPic;

    @ValidImage(message = "Pan required",
            groups = {CreateGroup.class})
    private MultipartFile panPic;

    @ValidPdf(message = "Adhar required",
            groups = {CreateGroup.class})
    private MultipartFile adharPdf;

    @ValidPdf(message = "Degree required",
            groups = {CreateGroup.class})
    private MultipartFile degreePdf;


    private MultipartFile experienceLetterPdf;
}