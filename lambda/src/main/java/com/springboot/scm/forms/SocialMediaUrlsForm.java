package com.springboot.scm.forms;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SocialMediaUrlsForm {
	
	@Pattern(
		    regexp = "^$|^(https?://)?(www\\.)?(youtube\\.com/(watch\\?v=|shorts/|live/)|youtu\\.be/)[A-Za-z0-9_-]+([&?].*)?$",
		    message = "Invalid YouTube video URL"
		)
	private String youtubeLink;
	
    @Pattern(
            regexp = "^$|^(https?://)?(www\\.)?instagram\\.com/.+",
            message = "Invalid Instagram URL"
    )
	private String instagramSortLink;
	
    @Pattern(
            regexp = "^$|^(https?://)?(www\\.)?facebook\\.com/.+",
            message = "Invalid Facebook URL"
    )
	private String facebookSortLink;
	
    
    @Pattern(
    	    regexp = "^$|^(https?://)?(www\\.)?(youtube\\.com/(watch\\?v=|shorts/|live/)|youtu\\.be/)[A-Za-z0-9_-]+([&?].*)?$",
    	    message = "Invalid YouTube video URL"
    	)
	private String youtubeSortLink;
	
	private String youtubeThumbnailLink;
	
	@NotBlank(message = "subject is required")
	@Size(min = 5,max = 200,message = "Subject  must be between 5 and 200 characters")
	private String subject;
	
	@NotBlank(message = "address is required")
	 @Size(min = 5,max = 5000)
	private String address;
	
	private MultipartFile emergencyVideo;

}
