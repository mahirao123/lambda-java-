package com.springboot.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.validator.CreateGroup;
import com.springboot.scm.validator.UpdateGroup;
import com.springboot.scm.validator.ValidImage;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SliderForm {
	
    @NotBlank(message="sliderType is required",groups = {CreateGroup.class, UpdateGroup.class})
    private String sliderType;
    
    @NotBlank(message="about is required",groups = {CreateGroup.class, UpdateGroup.class})
    private String about;

    
    private String mediaUrl;

//    @ValidImage(message = "File is  required",groups = {CreateGroup.class})
    private MultipartFile file; 
    
    private String bannerText;
	
}
