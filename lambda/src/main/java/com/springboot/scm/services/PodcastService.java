package com.springboot.scm.services;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.springboot.scm.entities.Podcast;

public interface PodcastService {
	
	Podcast savePodcast(Podcast podcast);
	
	Page<Podcast>getAllPodcast(int size, int page, String sortBy, String direction);
	
	Podcast updatePodcast(Podcast podcast);
	
	void deletePodcast(long id);
	
	Optional<Podcast> getById(Long id);
	
	Page<Podcast>searchByFieldsName(String hostName,String guestName, Date date,String status,int page,int size,String sortBy,String direction);

	public void updatePodcastStatuses();
}
