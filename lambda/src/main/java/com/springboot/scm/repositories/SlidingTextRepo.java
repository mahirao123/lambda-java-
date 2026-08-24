package com.springboot.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.scm.entitis.SlidingText;

@Repository
public interface SlidingTextRepo extends JpaRepository<SlidingText,Long>{

}
