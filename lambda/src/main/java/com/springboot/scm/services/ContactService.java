package com.springboot.scm.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.springboot.scm.entities.Contact;
import com.springboot.scm.entities.User;

public interface ContactService {
	//save contact
	Contact save(Contact contact);
	
//	update contact
	Contact update(Contact contact);
	
	//get All contacts
	List<Contact>getAll();
	
//	get contact by Id
	Contact getById(String id);
	
//	get contact by userId
	List<Contact> getByUserId(String userId);
	
	//get contact by user
	Page<Contact>getByUser(User user,int page,int size,String sortBy,String direction);
	
//	delete contact
	void delete(String id);
	
//	search Contact
	
 Page <Contact>searchByName(String nameKeyword,int page,int size,String sortBy,String order,User user);
	
 Page <Contact>searchByEmail(String emailKeyword,int page,int size,String sortBy,String order,User user);
	
 Page <Contact>searchByPhoneNumber(String phoneNumberKeyword,int page,int size,String sortBy,String order,User user);
	
	

}
