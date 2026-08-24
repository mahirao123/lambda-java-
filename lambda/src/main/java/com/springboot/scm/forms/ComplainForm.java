package com.springboot.scm.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ComplainForm {
	
	@NotBlank(message = "title is required")
	private String title;
	
	@NotBlank(message = " Form link is required")
	private String complainUrl;
	
	@NotBlank(message = "Result link is required")
	private String complainResultUrl;

}
