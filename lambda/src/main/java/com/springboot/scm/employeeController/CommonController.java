package com.springboot.scm.employeeController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.scm.employeeEntities.EmployeeDetails;
import com.springboot.scm.employeeServices.EmployeeService;
import com.springboot.scm.forms.EmployeeForm;
import com.springboot.scm.helpers.Message;
import com.springboot.scm.helpers.MessageType;
import com.springboot.scm.validator.UpdateGroup;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;



@RestController("/employee")
public class CommonController {

@Autowired	
private EmployeeService employee;	
	

}
