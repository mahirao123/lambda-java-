package com.springboot.scm.service_provider.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/service_provider")
public class ProviderLogin {
	
	@GetMapping("/login")
	public String providerLogin() {
		
		return "service_provider/service_provider_login";
	}

}
