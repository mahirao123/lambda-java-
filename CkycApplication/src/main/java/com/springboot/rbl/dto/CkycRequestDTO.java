package com.springboot.rbl.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CkycRequestDTO {
	
    private String fullName;
    private String dob;
    private String gender;
    private String pan;
    private String mobile;

}
