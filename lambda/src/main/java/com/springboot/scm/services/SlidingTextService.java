package com.springboot.scm.services;

import java.util.List;
import java.util.Optional;

import com.springboot.scm.entitis.SlidingText;

public interface SlidingTextService {
	
	SlidingText saveSlidingText(SlidingText text);
	
	SlidingText updateSlidingText(SlidingText text);
	
	Optional<SlidingText> getSlidingTextById(Long id);
	
	List<SlidingText>getAllSlidingText();
	
	void deleteSlidingText(Long id);

}
