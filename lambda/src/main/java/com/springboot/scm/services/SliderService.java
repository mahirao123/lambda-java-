package com.springboot.scm.services;

import java.util.List;
import java.util.Optional;

import com.springboot.scm.entities.Slider;

public interface SliderService {

	Slider saveMainSlider(Slider slider);
	
	List<Slider>getAllSlider();
	
	Slider updateMainSlider(Slider slider);
	
	void deleteMainSlider(String id);
	
	List<Slider> getActiveSlide();
	
	Optional<Slider> getBySliderId(String id);
	
}
