package com.springboot.scm.employeeRepositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.scm.employeeEntities.EmployeeDetails;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeDetails,String> {
	


	List<EmployeeDetails> findByRole(String role);
	
	Optional<EmployeeDetails> findByPhoneNumber(String phoneNumber);

	

	List<EmployeeDetails> findByName(String name);

	Optional<EmployeeDetails> findByEmail(String email);

	
	

}
