package com.springboot.scm.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.springboot.scm.employeeEntitis.EmployeeDetails;
import com.springboot.scm.employeeRepositories.EmployeeRepo;
import com.springboot.scm.entitis.User;
import com.springboot.scm.repositories.UserRepo;

@ControllerAdvice
public class RootController {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EmployeeRepo employeeRepo;

    @ModelAttribute("loggedInUser")
    public User loggedInUser(Authentication authentication) {

        if (authentication == null) {
            return null;
        }

        String email = authentication.getName();

        return userRepo.findByEmail(email)
                .orElse(null);
    }
    
    @ModelAttribute("loggedInEmployee")
    public EmployeeDetails loggedInEmployee(Authentication authentication) {

        if (authentication == null) {
            return null;
        }

        String email = authentication.getName();

        return employeeRepo.findByEmail(email)
                .orElse(null);
    }
}