package com.springboot.scm.admin_controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Admin_login {
	
	@GetMapping("/login")
	public String adminLoginPage() {
		
		return"admin/login";
	}

	@GetMapping("/dashboard")
	public String adminDashboardPage() {
		
		return"admin/dashboard";
	}
	
}
