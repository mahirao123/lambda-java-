package com.springboot.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.scm.entities.SlidingText;

@Repository
public interface SlidingTextRepo extends JpaRepository<SlidingText,Long>{

}
