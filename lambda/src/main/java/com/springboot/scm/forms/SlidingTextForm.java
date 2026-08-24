package com.springboot.scm.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SlidingTextForm {
	
	@NotBlank(message = "Text is required")
	 @Size(min = 5,max = 5000)
	private String text;
	
	private String url;
	
	

}
