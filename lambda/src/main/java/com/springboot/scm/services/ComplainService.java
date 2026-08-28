package com.springboot.scm.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.scm.entities.Complain;

@Service
public interface ComplainService {
	
	Complain saveCmplainFormUrl (Complain complain);
	
	void deleteComplainFormUrl(String id);
	
    List<Complain> getAllComplain();
}
