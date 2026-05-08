package com.springboot.scm.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springboot.scm.entitis.User;
import com.springboot.scm.helpers.Helper;
import com.springboot.scm.services.UserService;

@ControllerAdvice
public class RootController {
	
	@Autowired
	private UserService userService;
	
	Logger logger=LoggerFactory.getLogger(RootController.class);
	
	
	
	@ModelAttribute
	public void addLoggedInUserInformation(Model model, Authentication authentication) {
		
		if(authentication==null) {
			return;
		}
		
		System.out.println("addLoggedInUserInformation");
		
        String username=Helper.getEmailLoggedInUser(authentication);
		
		logger.info("loged in user: "+username);
		
		// database se data ko fetch kar sakte h
		
		User user=userService.getUserByEmail(username);
		
		
//        System.out.println(user.getUsername());
//        System.out.println(user.getPassword());
        
        model.addAttribute("loggedInUser",user);
		
	}

}
