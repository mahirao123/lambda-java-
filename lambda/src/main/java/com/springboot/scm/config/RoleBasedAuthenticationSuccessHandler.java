package com.springboot.scm.config;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RoleBasedAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        for (GrantedAuthority authority :
                authentication.getAuthorities()) {

            String role =
                    authority.getAuthority();

            System.out.println(
                    "Logged in Role: " + role);

            // ADMIN Redirect
            if (role.equals("ROLE_ADMIN")) {

                response.sendRedirect(
                        "/admin/dashboard");
                return;
            }

            // USER Redirect
            if (role.equals("ROLE_USER")) {

                response.sendRedirect(
                        "/user/profile");
                return;
            }

            // EMPLOYEE HR Redirect
            if (role.equals("ROLE_HR")) {

                response.sendRedirect(
                        "/hr/dashboard");
                return;
            }

            // Customer care Redirect
            if (role.equals("ROLE_CUSTOMER_CARE")) {

                response.sendRedirect(
                        "/employee/customerCare/dashboard");
                return;
            }
            
            // MANAGER Redirect
            if (role.equals("ROLE_MANAGER")) {

                response.sendRedirect(
                        "/manager/dashboard");
                return;
            }
            
            // Editor Redirect
            if (role.equals("ROLE_EDITOR")) {

                response.sendRedirect(
                        "/editor/dashboard");
                return;
            }


            // FINANCE Redirect
            if (role.equals("ROLE_FINANCE")) {

                response.sendRedirect(
                        "/finance/dashboard");
                return;
            }

            // SUPPORT Redirect
            if (role.equals("ROLE_PODCASTER")) {

                response.sendRedirect(
                        "production/podcastDashboard");
                return;
            }
            
            // SUPPORT Redirect
            if (role.equals("ROLE_ANCHOR")) {
            	
            	response.sendRedirect(
            			"production/anchorDashboard");
            	return;
            }

            // sales Redirect
            if (role.equals("ROLE_SALES")) {

                response.sendRedirect(
                        "/employee/sales/dashboard");
                return;
            }

            // SOCIAL MEDIA Redirect
            if (role.equals("ROLE_SOCIAL_MEDIA")) {

                response.sendRedirect(
                        "/socialMeadia/dashboard");
                return;
            }
        }

        // Default redirect
        response.sendRedirect("/home");
    }
}