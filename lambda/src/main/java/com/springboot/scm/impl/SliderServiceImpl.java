package com.springboot.scm.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entities.Slider;
import com.springboot.scm.repositories.SliderRepo;
import com.springboot.scm.services.SliderService;

@Service
public class SliderServiceImpl implements SliderService{

	@Autowired
	private SliderRepo sliderRepo;
	
	@Override
	public Slider saveMainSlider(Slider slider) {
		slider.setSliderId(UUID.randomUUID().toString());
	    
		return sliderRepo.save(slider);
		
	}

	@Override
	public List<Slider> getAllSlider() {
		
		return sliderRepo.findAll();
	}

	@Override
	public Slider updateMainSlider(Slider slider) {
	
		return sliderRepo.save(slider);
	}

	@Override
	public void deleteMainSlider(String id) {
		
		sliderRepo.deleteById(id);
		
	}

	@Override
	public List<Slider> getActiveSlide() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Slider> getBySliderId(String id) {
	Slider slider=sliderRepo.findById(id).orElseThrow(()->new RuntimeException("Not Found"));
		return Optional.of(slider);
	}

}
