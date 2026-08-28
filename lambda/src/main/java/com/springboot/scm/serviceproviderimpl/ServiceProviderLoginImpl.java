package com.springboot.scm.serviceproviderimpl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.scm.entities.ServiceProviderLogin;
import com.springboot.scm.repositories.ServiceProviderRepo;
import com.springboot.scm.serviceProviderService.ProviderService;


@Service
public class ServiceProviderLoginImpl implements ProviderService{

	@Autowired
	private ServiceProviderRepo serviceProviderRepo;
	
	@Override
	public ServiceProviderLogin saveLogin(ServiceProviderLogin provider) {
		// TODO Auto-generated method stub
		String id=UUID.randomUUID().toString();
		provider.setProviderId(id);
		return serviceProviderRepo.save(provider);
	}

	@Override
	public ServiceProviderLogin updateLogin(ServiceProviderLogin provider) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProviderLogin deleteLogin(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceProviderLogin getById(String providerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ServiceProviderLogin> getAll() {
		// TODO Auto-generated method stub
		return null;
	}



}
