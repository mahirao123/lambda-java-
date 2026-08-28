package com.springboot.scm.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SlidingText {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
	private String text;
	
	private String url;
	
	

}
