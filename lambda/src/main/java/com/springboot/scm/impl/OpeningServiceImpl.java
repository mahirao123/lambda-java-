package com.springboot.scm.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entities.Opening;
import com.springboot.scm.repositories.OpeningRepo;
import com.springboot.scm.services.OpeningService;

@Service
public class OpeningServiceImpl implements OpeningService{
	
	@Autowired
	private OpeningRepo openingRepo;

	@Override
	public Opening saveOpening(Opening opening) {
		
		String openingId=UUID.randomUUID().toString();
		opening.setId(openingId);
		
		return openingRepo.save(opening);
	}

	@Override
	public void deleteOpening(String id) {
	  
		openingRepo.deleteById(id);
		
	}

	@Override
	public List<Opening> getAllOpening() {
		
		return openingRepo.findAll();
	}

	@Override
	public Opening updateOpening(Opening opening) {
		
		return openingRepo.save(opening);
	}

	@Override
	public Optional<Opening> getById(String id) {
		// TODO Auto-generated method stub
		return openingRepo.findById(id);
	}

}
