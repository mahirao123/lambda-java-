package com.springboot.scm.services;

import java.util.List;
import java.util.Optional;

import com.springboot.scm.entities.Opening;

public interface OpeningService {

	Opening saveOpening(Opening opening);
	
	Opening updateOpening(Opening opening);
	
	void deleteOpening(String id);
	
	List<Opening> getAllOpening();

	Optional<Opening> getById(String id);

}
