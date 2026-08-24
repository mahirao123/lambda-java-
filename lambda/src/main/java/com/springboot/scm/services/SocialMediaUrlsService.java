package com.springboot.scm.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.springboot.scm.employeeEntitis.EmployeeDetails;
import com.springboot.scm.entitis.SocialMediaUrls;

public interface SocialMediaUrlsService {
	
	SocialMediaUrls saveSocialMediaUrlsLink(SocialMediaUrls url);
	
	SocialMediaUrls updateSocialMediaUrlsLink(SocialMediaUrls url);
	
	Page<SocialMediaUrls>getAll(int size, int page, String sortBy, String direction);
	
	Page<SocialMediaUrls>getAllByEmployee(EmployeeDetails employee,int page,int size,String sortBy,String direction);
	
	void deletSocilaMediaUrlsLink(String id);
	
	Optional<SocialMediaUrls> getById(String id);
	
	Page<SocialMediaUrls>searchBySubjectOrAddress(String subjectKeyword,String addressKeyword,int page,int size,String sortBy,String direction);	
	
	Page<SocialMediaUrls>searchByEmployeeAndSubjectOrAddress(EmployeeDetails employee,String Keyword,int page,int size,String sortBy,String direction);
	
    Page<SocialMediaUrls>searchByDateTime(LocalDateTime dateTime,int page,int size,String sortBy,String direction);
}
