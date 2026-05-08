package com.springboot.scm.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.scm.entitis.Contact;
import com.springboot.scm.entitis.User;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.helpers.Helper;
import com.springboot.scm.repositories.UserRepo;
import com.springboot.scm.services.ContactService;
import com.springboot.scm.services.UserService;


@Controller
@RequestMapping("/user")
public class UserController {
	
	private Logger logger= LoggerFactory.getLogger(UserController.class);
	
   @Autowired	
   private UserService userService;
   
   @Autowired	
   private ContactService contactService;
	
	@RequestMapping(value="/dashboard")
	public String userDashboard(@RequestParam(value="page",defaultValue="0") int page,
            @RequestParam(value="size",defaultValue=AppConstants.PAGE_SIZE) int size,
            @RequestParam(value="sortBy",defaultValue="name") String sortBy,
            @RequestParam(value="direction",defaultValue="asc") String direction,
            Model model,Authentication authentication) {
		//add contact page
		String username=Helper.getEmailLoggedInUser(authentication);
		var user=userService.getUserByEmail(username);
		Page<Contact> pageContacts=contactService.getByUser(user,page,size,sortBy,direction);
		if (pageContacts == null) {
		    pageContacts = Page.empty();
		}
		
		 if (page < 0) {
		        page = 0;
		    }
		 
		model.addAttribute("pageContacts",pageContacts);
		model.addAttribute("pageSize",AppConstants.PAGE_SIZE);
//		model.addAttribute("contactSearchForm",contactSearchForm);
		//view contact page
		//user edit contact
		//
		
		return"user/dashboard";
	}
	
//user profile page
	
	@RequestMapping(value="/profile")
	public String userProfile(Model model,Authentication authentication) {
		
		
		
		//add contact page
		//view contact page
		String username=Helper.getEmailLoggedInUser(authentication);
		var user=userService.getUserByEmail(username);
		//user edit contact
		//
		model.addAttribute("loggedInUser", user);
		return"user/profile";
	}
}
