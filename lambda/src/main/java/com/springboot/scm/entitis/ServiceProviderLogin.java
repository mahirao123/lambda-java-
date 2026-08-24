package com.springboot.scm.entitis;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceProviderLogin {
	
	@Id
	private String ProviderId;
	
	private String username;
	
	private String password;

}
