package com.springboot.scm.services;

import java.sql.Date;
import java.util.List;

import com.springboot.scm.entities.ContentCategory;

public interface ContentCategoryService {

    ContentCategory save(ContentCategory category);
    
    ContentCategory updateCategory(ContentCategory category);

    List<ContentCategory> findAll();

    ContentCategory findById(Long id);
    
    List<ContentCategory> getAll();
    
    List<ContentCategory> searchByDay(String day);
    
    ContentCategory searchByDate(Date date);
    
 
    
    void delete(Long id);
}