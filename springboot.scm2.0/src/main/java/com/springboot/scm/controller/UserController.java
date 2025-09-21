package com.springboot.scm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping(value="/dashboard")
	public String userDashboard() {
		//add contact page
		//view contact page
		//user edit contact
		//
		
		return"user/dashboard";
	}
	
//user profile page
	
	@RequestMapping(value="/profile")
	public String userProfile() {
		//add contact page
		//view contact page
		//user edit contact
		//
		
		return"user/profile";
	}
}
