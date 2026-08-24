package com.springboot.scm.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.util.StringUtils;
import com.springboot.scm.employeeEntitis.EmployeeDetails;
import com.springboot.scm.entitis.SocialMediaUrls;
import com.springboot.scm.repositories.SocialMediaUrlsRepo;
import com.springboot.scm.services.SocialMediaUrlsService;
import com.springboot.scm.util.SocialMediaUrlUtil;


@Service
public class SocialMediaUrlsImpl implements SocialMediaUrlsService{
	
	@Autowired
	private SocialMediaUrlsRepo socialRepo;

	@Override
	public SocialMediaUrls saveSocialMediaUrlsLink(SocialMediaUrls url) {
		
	
		
		  if(StringUtils.hasText(url.getYoutubeLink())){

			  url.setYoutubeEmbedLink(SocialMediaUrlUtil.youtubeEmbedUrl(url.getYoutubeLink()));

		        	url.setYoutubeThumbnailLink(SocialMediaUrlUtil.youtubeThumbnail(url.getYoutubeLink()));
		        }
		  
		  if(StringUtils.hasText(url.getYoutubeSortLink())){
			  
			  url.setYoutubeSortEmbedLink(SocialMediaUrlUtil.youtubeEmbedUrl(url.getYoutubeSortLink()));			  
				  
				  url.setYoutubeSortThumbnailLink(SocialMediaUrlUtil.youtubeThumbnail(url.getYoutubeSortLink()));
			  }
		
		return socialRepo.save(url);
		
	}

	@Override
	public SocialMediaUrls updateSocialMediaUrlsLink(SocialMediaUrls url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SocialMediaUrls> getAll(int size, int page, String sortBy, String direction) {
		Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		
		return socialRepo.findAll(pageable);
	}

	@Override
	public Page<SocialMediaUrls> getAllByEmployee(EmployeeDetails employee, int page, int size, String sortBy,
			String direction) {
		
		return null;
	}

	@Override
	public void deletSocilaMediaUrlsLink(String id) {
		socialRepo.deleteById(id);
		
	}

	@Override
	public Page<SocialMediaUrls> searchBySubjectOrAddress(String subjectKeyword, String addressKeyword, int page,
			int size, String sortBy, String direction) {
		Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		
		return socialRepo.findBySubjectContainingIgnoreCaseOrAddressContainingIgnoreCaseOrderByDateTimeDesc(subjectKeyword, addressKeyword, pageable);
	}

	@Override
	public Page<SocialMediaUrls> searchByEmployeeAndSubjectOrAddress(EmployeeDetails employee, String Keyword, int page,
			int size, String sortBy, String direction) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<SocialMediaUrls> searchByDateTime(LocalDateTime dateTime,int page,int size,String sortBy,String direction) {
		Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return socialRepo.findByDateTimeBeforeOrderByDateTimeDesc(dateTime,pageable);
	}

	@Override
	public Optional<SocialMediaUrls> getById(String id) {
		return socialRepo.findById(id);
		
	}


}
