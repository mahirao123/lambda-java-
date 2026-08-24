package com.springboot.scm.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entitis.SlidingText;
import com.springboot.scm.repositories.SlidingTextRepo;
import com.springboot.scm.services.SlidingTextService;

@Service
public class SlidingTextServiceImpl implements SlidingTextService{
	@Autowired
	private SlidingTextRepo slidingTextRepo;

	@Override
	public SlidingText saveSlidingText(SlidingText text) {
		
	
		return slidingTextRepo.save(text);
	}

	@Override
	public SlidingText updateSlidingText(SlidingText text) {
		
		return slidingTextRepo.save(text);
	}

	@Override
	public List<SlidingText> getAllSlidingText() {
		
		return slidingTextRepo.findAll();
	}

	@Override
	public void deleteSlidingText(Long id) {
		slidingTextRepo.deleteById(id);
		
	}

	@Override
	public Optional<SlidingText> getSlidingTextById(Long id) {
		
		return slidingTextRepo.findById(id);
		
	}

}
