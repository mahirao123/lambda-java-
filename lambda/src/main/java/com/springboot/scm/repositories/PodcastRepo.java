package com.springboot.scm.repositories;

import java.sql.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.scm.entities.Podcast;

@Repository
public interface PodcastRepo  extends JpaRepository<Podcast,Long>{
	
	Page<Podcast> findByHostNameIgnoreCaseOrGuestNameIgnoreCaseOrDateOrEnableIgnoreCaseOrderByDateDesc
    (String hostName,String guestName, Date date,String status,Pageable pageable);
}
