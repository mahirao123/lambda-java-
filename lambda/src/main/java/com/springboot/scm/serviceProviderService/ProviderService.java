package com.springboot.scm.serviceProviderService;

import java.util.List;

import com.springboot.scm.entitis.ServiceProviderLogin;

public interface ProviderService {
	
	// save Service Provider
	ServiceProviderLogin saveLogin(ServiceProviderLogin provider);
	
	// update Service Provider
	ServiceProviderLogin updateLogin(ServiceProviderLogin provider);
	
	// delete Service Provider
	ServiceProviderLogin deleteLogin(String id);
	
	// get by id Service Provider
	ServiceProviderLogin getById(String providerId);
	
	//get all ServiceProvider
	
	List<ServiceProviderLogin> getAll();

}
