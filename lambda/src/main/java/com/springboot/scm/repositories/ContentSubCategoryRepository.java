package com.springboot.scm.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springboot.scm.entities.ContentSubCategory;

public interface ContentSubCategoryRepository
        extends JpaRepository<ContentSubCategory, Long> {

    List<ContentSubCategory> findByCategoryId(Long categoryId);
    
   List <ContentSubCategory> findByNameContainingIgnoreCase(String name);

}