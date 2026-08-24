package com.springboot.scm.serviceProvider_controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.scm.repositories.ServiceProviderRepo;
import com.springboot.scm.serviceProviderService.ProviderService;

@Controller
@RequestMapping("/serviceProvider")
public class ProviderController {

	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	@Autowired
	private ProviderService providerService;
	
   @GetMapping("/login")
	public String providerLogedIn() {
		
//	   String existingProvidername=serviceProviderRepo.findById(null).;
		
		return "service_provider/login";
		
	}
}
