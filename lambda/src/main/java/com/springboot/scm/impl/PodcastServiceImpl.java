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

import com.springboot.scm.entities.Podcast;
import com.springboot.scm.repositories.PodcastRepo;
import com.springboot.scm.services.PodcastService;

@Service
public class PodcastServiceImpl implements PodcastService{

	@Autowired
	private PodcastRepo podcastRepo;
	

    // =========================================================
    // UPDATE STATUS OF ONE PODCAST
    // =========================================================

    private void updatePodcastStatus(Podcast podcast) {

        if (podcast.getDate() == null) {
            podcast.setEnable(null);
            return;
        }

        LocalDate today = LocalDate.now();

        LocalDate podcastDate = podcast.getDate().toLocalDate();


        // Date already passed
        if (podcastDate.isBefore(today)) {

            podcast.setEnable("End");

        }

        // Future date
        else if (podcastDate.isAfter(today)) {

            podcast.setEnable("Upcoming");

        }

        // Today's date
        else {

            podcast.setEnable("Running");
        }
    }


    // =========================================================
    // SAVE PODCAST
    // =========================================================

    @Override
    public Podcast savePodcast(Podcast podcast) {

        updatePodcastStatus(podcast);

        return podcastRepo.save(podcast);
    }


    // =========================================================
    // UPDATE PODCAST
    // =========================================================

    @Override
    public Podcast updatePodcast(Podcast podcast) {

        updatePodcastStatus(podcast);

        return podcastRepo.save(podcast);
    }


    // =========================================================
    // AUTOMATICALLY UPDATE EVERY MIDNIGHT
    // INDIA TIME
    // =========================================================

    @Override
    @Scheduled(
        cron = "0 0 0 * * *",
        zone = "Asia/Kolkata"
    )
    public void updatePodcastStatuses() {

        System.out.println("======================================");
        System.out.println("Updating Podcast Statuses...");
        System.out.println("Today: " + LocalDate.now());
        System.out.println("======================================");

        List<Podcast> podcasts = podcastRepo.findAll();

        for (Podcast podcast : podcasts) {

            updatePodcastStatus(podcast);

            System.out.println(
                "Podcast ID: " + podcast.getId()
                + " | Date: " + podcast.getDate()
                + " | Status: " + podcast.getEnable()
            );
        }

        podcastRepo.saveAll(podcasts);

        System.out.println("Podcast statuses updated successfully.");
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
