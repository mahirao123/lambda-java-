package com.springboot.scm.helpers;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
	
	public static String getEmailLoggedInUser(Authentication authentication) {
		
	// ager email aur password se login kiye h to email kaise nikalenge
		
//		 Principal principal = (Principal) authentication.getPrincipal();
		
		
		if(authentication instanceof OAuth2AuthenticationToken) {
			
			var oAuth2AuthenticationToken =  (OAuth2AuthenticationToken) authentication;
			String clientId= oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
			
			var oauth2User=(OAuth2User)authentication.getPrincipal();
			
			String username="";
//			System.out.println("clientId "+clientId);
			
			// google login
			if(clientId.equalsIgnoreCase("google")){
				
//				System.out.println("Login from google");
			 username=	oauth2User.getAttribute("email");
				
			}
			
			//gitHub login
			else if(clientId.equalsIgnoreCase("github")) {
				
//				System.out.println("Login from github");
				username=oauth2User.getAttribute("email")!=null ? oauth2User.getAttribute("email")
	    		         : oauth2User.getAttribute("login").toString()+"@gmail.com";
				
				
			}
			
			return username;
			
		}
		
		
		

		
		//other if any
		
		
		else {
			return authentication.getName();
		}
		
		
		
	}

}
