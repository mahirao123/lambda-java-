package com.springboot.scm.forms;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Data
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactSearchForm {
	
	private String field;
	
	private String value;

}
