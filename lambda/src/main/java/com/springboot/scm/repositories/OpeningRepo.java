package com.springboot.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.scm.entities.Opening;

@Repository
public interface OpeningRepo extends JpaRepository<Opening,String>{
	

}
