package com.springboot.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.springboot.scm.employeeEntities.EmployeeDetails;
import com.springboot.scm.employeeImpl.EmployeePrincipal;

@Component
public class EmployeeHelper {

    public static EmployeeDetails getLoggedInEmployee() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        if (principal instanceof EmployeePrincipal employeePrincipal) {
            return employeePrincipal.getEmployee();
        }

        return null;
    }
}