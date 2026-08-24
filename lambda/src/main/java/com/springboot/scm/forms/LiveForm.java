package com.springboot.scm.forms;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LiveForm {
	
	@Pattern(
		    regexp = "^(https?://)([\\w-]+\\.)+[\\w-]+(:\\d+)?(/[^\\s]*)?$",
		    message = "Please enter a valid HTTP/HTTPS URL"
		)
	
	private String liveUrl;

}

