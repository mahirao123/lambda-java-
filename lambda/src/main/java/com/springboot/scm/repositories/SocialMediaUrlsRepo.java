package com.springboot.scm.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.springboot.scm.employeeEntities.EmployeeDetails;
import com.springboot.scm.entities.SocialMediaUrls;

@Repository
public interface SocialMediaUrlsRepo extends JpaRepository<SocialMediaUrls,String> {
	
//	custom finder method
	Page <EmployeeDetails> findByEmployee(EmployeeDetails employee ,Pageable pageable);
	
//custom query method to get all contacts of a user
	@Query("SELECT s from SocialMediaUrls s WHERE s.employee.id= :employeeId")
	List <SocialMediaUrls> findByEmployeeId(@Param("employeeId") String employeeId);
	
//	custom finder method	

	@Query("""
				SELECT s FROM SocialMediaUrls s WHERE s.employee = :employee AND (
				    LOWER(s.address) LIKE LOWER(CONCAT('%', :keyword, '%'))
				    OR LOWER(s.subject) LIKE LOWER(CONCAT('%', :keyword, '%'))
				)
			""")
	Page<SocialMediaUrls> findByEmployee(@Param("employee") EmployeeDetails employee, @Param("keyword") String keyword,Pageable pageable);
	
	Page<SocialMediaUrls> findBySubjectContainingIgnoreCaseOrAddressContainingIgnoreCaseOrderByDateTimeDesc
	                                            (String subject,String address,Pageable pageable);
	
	
	Page<SocialMediaUrls> findByEmployeeAndSubjectContainingIgnoreCaseOrAddressContainingIgnoreCaseOrderByDateTimeDesc 
	                                            (EmployeeDetails employee ,String subject,String address,Pageable pageable);
	
	List<SocialMediaUrls> findTop10ByIdOrderByDateTimeDesc(String id);

	Page<SocialMediaUrls>findByDateTimeBeforeOrderByDateTimeDesc(LocalDateTime dateTime,Pageable pageable);
}
