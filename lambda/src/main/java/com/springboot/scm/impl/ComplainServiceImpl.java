package com.springboot.scm.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entitis.Complain;
import com.springboot.scm.repositories.ComplainRepo;
import com.springboot.scm.services.ComplainService;

@Service
public class ComplainServiceImpl implements ComplainService{

	@Autowired
	private ComplainRepo complainRepo;
	
	@Override
	public Complain saveCmplainFormUrl(Complain complain) {
		
		
		
		return complainRepo.save(complain);
	}

	@Override
	public void deleteComplainFormUrl(String id) {

		
		complainRepo.deleteById(id);
	}

	@Override
	public List<Complain> getAllComplain() {
		
		return complainRepo.findAll();
	}

}
