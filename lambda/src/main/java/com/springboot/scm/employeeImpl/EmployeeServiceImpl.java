package com.springboot.scm.employeeImpl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springboot.scm.employeeEntitis.EmployeeDetails;
import com.springboot.scm.employeeRepositories.EmployeeRepo;
import com.springboot.scm.employeeServices.EmployeeService;
import com.springboot.scm.helpers.UserAlreadyExistsException;

@Service
public class EmployeeServiceImpl implements EmployeeService {
   
    @Autowired
    private PasswordEncoder passwordEncoder;
    
	@Autowired
	private EmployeeRepo employeeRepo;
	
	@Override
	public EmployeeDetails saveEmployeeDetails(EmployeeDetails employeeDetails) {
		

		String employeeId=UUID.randomUUID().toString();
		employeeDetails.setEmployeeId(employeeId);
		//Encode password
		employeeDetails.setPassword(passwordEncoder.encode(employeeDetails.getPassword()));
        // Check email already exists
		
	   Optional  <EmployeeDetails> existEmployee=	employeeRepo.findByEmail(employeeDetails.getEmail());
			
		if(existEmployee.isPresent()) {
			throw new UserAlreadyExistsException("This email is already registered: ");
		}


		return employeeRepo.save(employeeDetails);
	}

	@Override
	public EmployeeDetails updateEmployeeDetails(EmployeeDetails employeeDetails) {
		
		
		return employeeRepo.save(employeeDetails);
	}

	@Override
	public void deleteEmployeeDetails(String id) {
		 employeeRepo.deleteById(id);
		
	}


	@Override
	public List<EmployeeDetails> getByRole(String role) {
		
	    return	employeeRepo.findByRole(role);
	}
	


	@Override
	public List<EmployeeDetails> getEmployeeByName(String name) {
		
	    return	employeeRepo.findByName(name);
		
	}

	@Override
	public Optional<EmployeeDetails> getEmployeeByEmail(String email) {
		
	    return	Optional.of(employeeRepo.findByEmail(email).orElseThrow(()-> new RuntimeException(" Employee Not Found")));
	}
	

	@Override
	public Page<EmployeeDetails> getAll(int size, int page, String sortBy, String direction) {
		Sort sort=direction.equals("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
		var pageable=PageRequest.of(page, size,sort);
	    return	employeeRepo.findAll(pageable);
	}

	@Override
	public boolean isEmployeeExist(String Id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmployeeExistByEmail(String email) {
		
		EmployeeDetails employee=employeeRepo.findByEmail(email).orElse(null);
		
		return employee!=null ? true :false;
	}

	@Override
	public Optional<EmployeeDetails> getByEmployeeId(String id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(employeeRepo.findById(id).orElse(null));
	}

	@Override
	public Optional getByPhoneNumber(String phoneNumber) {
		// TODO Auto-generated method stub
		return employeeRepo.findByPhoneNumber(phoneNumber);
	}







}
