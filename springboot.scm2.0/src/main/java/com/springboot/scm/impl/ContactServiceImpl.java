package com.springboot.scm.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.springboot.scm.entitis.Contact;
import com.springboot.scm.entitis.User;
import com.springboot.scm.helpers.ResourceNotFoundException;
import com.springboot.scm.repositories.ContactRepo;
import com.springboot.scm.services.ContactService;

@Service
public class ContactServiceImpl implements ContactService{

	@Autowired
	private ContactRepo contactRepo;
	
	@Override
	public Contact save(Contact contact) {
		
		String contactId=UUID.randomUUID().toString();
		contact.setId(contactId);
		contactRepo.save(contact);
		return null;
	}

	@Override
	public Contact update(Contact contact) {
		Contact existingContact = contactRepo.findById(contact.getId())
	            .orElseThrow(() -> new RuntimeException("Contact not found"));

	    existingContact.setName(contact.getName());
	    existingContact.setEmail(contact.getEmail());
	    existingContact.setPhoneNumber(contact.getPhoneNumber());
	    existingContact.setAddress(contact.getAddress());
	    existingContact.setDescription(contact.getDescription());
	    existingContact.setWebsiteLink(contact.getWebsiteLink());
	    existingContact.setLinkedInLink(contact.getLinkedInLink());
	    existingContact.setFavorite(contact.isFavorite());
	    
	    if(contact.getPicture()!=null && !contact.getPicture().isEmpty()) {
	    	 existingContact.setPicture(contact.getPicture());
	    }
	   

	   return contactRepo.save(existingContact);
		
	}

	@Override
	public List<Contact> getAll() {
	
		return contactRepo.findAll();
	}

	@Override
	public Contact getById(String id) {
		
		return contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact Not Found with given Id"+id));
	}

	@Override
	public List<Contact> getByUserId(String userId) {
	
		return contactRepo.findByUserId(userId);
	}

	@Override
	public void delete(String id) {
		
		var contact=contactRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Contact Not Found with given Id"+id));
		contactRepo.delete(contact);
		
	}

	@Override
	public Page<Contact> getByUser(User user,int page,int size,String sortBy,String direction) {
		
		Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
	return	contactRepo.findByUser(user,pageable);
	
	}

	@Override
	public Page<Contact> searchByName(String nameKeyword, int page, int size, String sortBy, String order,User user) {
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndNameContaining(user,nameKeyword, pageable);
	}

	@Override
	public Page<Contact> searchByEmail(String emailKeyword, int page, int size, String sortBy, String order,User user) {
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndEmailContaining(user,emailKeyword, pageable);
	}

	@Override
	public Page<Contact> searchByPhoneNumber(String phoneNumberKeyword, int page, int size, String sortBy,
			String order,User user) {
		Sort sort=order.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
		return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumberKeyword, pageable);
	}



}
