package com.springboot.scm.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProviderLoginForm {
	
	
	@NotBlank(message="email is required")
	@Email(message="Invalid email address")
	private String email;
	
	@NotBlank(message="password is required")
	@Size(min=6, message="minimum 6 characters is requird")
	private String password;
}
