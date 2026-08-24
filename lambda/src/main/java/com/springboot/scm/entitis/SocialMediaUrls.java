package com.springboot.scm.entitis;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.employeeEntitis.EmployeeDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SocialMediaUrls {
	
	@Id
	private String id;
	
	private String address;
	
	private String subject;
	
	private String  youtubeEmbedLink;
	
	private String  youtubeSortEmbedLink;
	
	private String youtubeThumbnailLink;
	
	private String youtubeSortThumbnailLink;
	
	private String youtubeLink;
	
	private String instagramSortLink;
	
	private String facebookSortLink;
	
	private String youtubeSortLink;
	
	private String emergencyVideo;
	
	private String mediaType;
	
	private LocalDateTime dateTime;
	
	@ManyToOne
	private EmployeeDetails employee;

	
//	public String getEmbedUrl() {
//	    if (youtubeLink == null) return "";
//
//	    if (youtubeLink.contains("watch?v=")) {
//	        String id = youtubeLink.substring(youtubeLink.indexOf("watch?v=") + 8);
//	        return "https://www.youtube.com/embed/" + id;
//	    }
//
//	    return youtubeLink;
//	}
}
