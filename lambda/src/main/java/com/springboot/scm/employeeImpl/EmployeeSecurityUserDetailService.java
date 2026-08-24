package com.springboot.scm.employeeImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springboot.scm.employeeEntitis.EmployeeDetails;
import com.springboot.scm.employeeRepositories.EmployeeRepo;

@Service
public class EmployeeSecurityUserDetailService
        implements UserDetailsService {

    @Autowired
    private EmployeeRepo employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {

        EmployeeDetails employee =
                employeeRepository
                        .findByEmail(email)
                        .orElseThrow(() ->
                                new UsernameNotFoundException(
                                        "Employee not found"));

        return new EmployeePrincipal(employee);
    }
}