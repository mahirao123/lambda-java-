package com.springboot.scm.employeeServices;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.springboot.scm.employeeEntities.EmployeeDetails;

public interface EmployeeService {

	EmployeeDetails	saveEmployeeDetails(EmployeeDetails employeeDetails);
	
	
	EmployeeDetails	updateEmployeeDetails(EmployeeDetails employeeDetails);
	
	void deleteEmployeeDetails(String id);
	
	
	 Optional<EmployeeDetails>	getByEmployeeId(String id);
	
	List<EmployeeDetails>	getEmployeeByName(String name);
	
	Optional<EmployeeDetails>	getEmployeeByEmail(String email);
	
	
	List<EmployeeDetails> getByRole(String role);
	
	Optional <EmployeeDetails> getByPhoneNumber(String phoneNumber);

	
	Page<EmployeeDetails> getAll(int size,int page,String sortBy, String direction);
	
	
	boolean isEmployeeExist(String Id);
	
	
	boolean isEmployeeExistByEmail(String email);


	
	
	
	
	
	
	
}
