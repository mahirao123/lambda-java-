package com.springboot.scm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entitis.Live;
import com.springboot.scm.repositories.LiveRepo;
import com.springboot.scm.services.LiveService;

@Service
public class LiveServiceImpl implements LiveService{
	
	@Autowired
	private LiveRepo liveRepo;
	
	@Override
	public Live saveLiveUrl(Live live) {
		
		return liveRepo.save(live);
	}

	@Override
	public List<Live> getLiveUrl() {
		
		return liveRepo.findAll();
	}

	@Override
	public void deleteLiveUrl(Long id) {
		liveRepo.deleteById(id);
		
	}



}
