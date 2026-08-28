package com.springboot.scm.entities;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Opening {
	
	@Id
	private String id;
	
	private String role;
	
	private String link;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String about;

}
