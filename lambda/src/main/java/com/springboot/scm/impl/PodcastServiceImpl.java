package com.springboot.scm.impl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.springboot.scm.entitis.Podcast;
import com.springboot.scm.repositories.PodcastRepo;
import com.springboot.scm.services.PodcastService;

@Service
public class PodcastServiceImpl implements PodcastService{

	@Autowired
	private PodcastRepo podcastRepo;
	
    private void updatePodcastStatus(Podcast podcast) {

        if (podcast.getDate() == null) {
            podcast.setEnable(null);
            return;
        }

        LocalDate today = LocalDate.now();
        LocalDate podcastDate = podcast.getDate().toLocalDate();

        if (podcastDate.isBefore(today)) {
            podcast.setEnable("End");

        } else if (podcastDate.isAfter(today)) {
            podcast.setEnable("Upcoming");

        } else {
            podcast.setEnable("Running");
        }
    }


    @Override
    public Podcast savePodcast(Podcast podcast) {

        updatePodcastStatus(podcast);

        return podcastRepo.save(podcast);
    }


    @Override
    public Podcast updatePodcast(Podcast podcast) {

        updatePodcastStatus(podcast);

        return podcastRepo.save(podcast);
    }


    @Override
    @Scheduled(cron = "0 0 0 * * *")
    public void updatePodcastStatuses() {

        List<Podcast> podcasts = podcastRepo.findAll();

        for (Podcast podcast : podcasts) {
            updatePodcastStatus(podcast);
        }

        podcastRepo.saveAll(podcasts);
    }	

	
	
	@Override
	public Page<Podcast> getAllPodcast(int size, int page, String sortBy, String direction) {
		
		Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return podcastRepo.findAll(pageable);
	}


	@Override
	public void deletePodcast(long id) {
		podcastRepo.deleteById(id);
		
	}

	@Override
	public Optional<Podcast> getById(Long id) {
		
		return podcastRepo.findById(id);
	}

	@Override
	public Page<Podcast> searchByFieldsName(String hostName, String guestName, Date date, String status, int page,
			int size, String sortBy, String direction) {
		Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		
		return podcastRepo.findByHostNameIgnoreCaseOrGuestNameIgnoreCaseOrDateOrEnableIgnoreCaseOrderByDateDesc
		(hostName, guestName, date, status, pageable);
		
	}

}
