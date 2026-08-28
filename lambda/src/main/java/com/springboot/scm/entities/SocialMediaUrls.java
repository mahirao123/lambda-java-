package com.springboot.scm.entities;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.springboot.scm.employeeEntities.EmployeeDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employeeId", nullable = true)
	private EmployeeDetails employee;

	private String postedBy;


}
