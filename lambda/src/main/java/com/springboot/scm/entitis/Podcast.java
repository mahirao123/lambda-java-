package com.springboot.scm.entitis;

import java.sql.Date;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Podcast {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String hostName;
	
	private String guestName;
	
	private Date date;
	
	private LocalTime time;
	
	private String duration;
	
	private String podcastUrl;
	
	private String remark;
	
	private String enable;
	

}
