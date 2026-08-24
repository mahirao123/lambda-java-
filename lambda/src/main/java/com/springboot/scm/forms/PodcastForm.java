package com.springboot.scm.forms;

import java.sql.Date;
import java.time.LocalTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PodcastForm {
	
	@NotBlank(message="Host name is required")
	private String hostName;
	
	@NotBlank(message="Guest name is required")
	private String guestName;
	
	@NotNull(message = "Date is required")
	private Date date;
	
	private LocalTime time;
	
	private String duration;
	
	@NotBlank(message="Url  is required")
	private String podcastUrl;
	
	private String remark;

}
