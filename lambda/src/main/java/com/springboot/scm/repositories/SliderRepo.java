package com.springboot.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.scm.entities.Slider;

@Repository
public interface SliderRepo extends JpaRepository<Slider,String>{
	List<Slider> findByActiveTrue();

}
