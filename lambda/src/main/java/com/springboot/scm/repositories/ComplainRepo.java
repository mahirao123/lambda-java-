package com.springboot.scm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.springboot.scm.entitis.Complain;

@Repository
public interface ComplainRepo  extends JpaRepository<Complain,String>{

}
