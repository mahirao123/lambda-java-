package com.springboot.scm.config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.springboot.scm.entitis.Providers;
import com.springboot.scm.entitis.User;
import com.springboot.scm.helpers.AppConstants;
import com.springboot.scm.repositories.UserRepo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class OAuthAthenticationSuccessHandler implements AuthenticationSuccessHandler{

	Logger logger= LoggerFactory.getLogger(OAuthAthenticationSuccessHandler.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request,
			HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		    logger.info("OAuthAthenticationSuccessHandler");

		    
		    // checking for provider like google,faceBook,self,gitHub
		    
		var oAuth2AuthenticationToken =  (OAuth2AuthenticationToken) authentication;
		   String authrizedClientRegistrationId= oAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
		   
		   logger.info(authrizedClientRegistrationId);
		   
		   var oauthUser=(DefaultOAuth2User)authentication.getPrincipal();
		   oauthUser.getAttributes().forEach((key,value)->{
			  logger.info(key+" : "+value);
			  
		   });
		   
		   User user =new User();
		   
		   user.setUserId(UUID.randomUUID().toString());

		   user.setRoleList(List.of(AppConstants.ROLE_USER));
		   user.setEmailVerified(true);
		   user.setEnabled(true);
		   user.setPassword("123456789");
		   
		    //google login
		    if(authrizedClientRegistrationId.equalsIgnoreCase("google")) {
		    	user.setEmail(oauthUser.getAttribute("email").toString());
		    	user.setProfilePic(oauthUser.getAttribute("picture").toString());
		    	user.setName(oauthUser.getAttribute("name").toString());
		    	user.setProviderUserId(oauthUser.getName());
		    	user.setProvider(Providers.GOOGLE);
		    	user.setAbout("This is login by google");
		    }
		    
		    //gitHub login
		    else if(authrizedClientRegistrationId.equalsIgnoreCase("github")) {
		    	
		    	String email=oauthUser.getAttribute("email")!=null ? oauthUser.getAttribute("email")
		    		         : oauthUser.getAttribute("login").toString()+"@gmail.com";
		    	
		    	String picture=oauthUser.getAttribute("avatar_url").toString();
		    	String name=oauthUser.getAttribute("login").toString();
		    	String providerUserId=oauthUser.getName();
		    	
		    	user.setEmail(email);
		    	user.setProfilePic(picture);
		    	user.setName(name);
		    	user.setProviderUserId(providerUserId);
		    	user.setProvider(Providers.GITHUB);
		    	user.setAbout("Login with github ");
		    	
		    }
		    //LinkedIn login
		    
//		    else if(authrizedClientRegistrationId.equalsIgnoreCase("linkedin")) {
//		    	
//		    }
		    
		    //other provider
		    else {
		    	logger.info("OAuthAuthenticationHandler :Unknown Provider ");
		    }
		    // take data from google
		   /* 
		    DefaultOAuth2User user =  (DefaultOAuth2User) authentication.getPrincipal();
		    String email=user.getAttribute("email").toString();
		    String name=user.getAttribute("name").toString();
		    String picture=user.getAttribute("picture").toString();
		    
		    //created user and save in database
		    
		    User user1=new User();
		    user1.setEmail(email);
		    user1.setName(name);
		    user1.setProfilePic(picture);
		    user1.setPassword("password");
		    user1.setUserId(UUID.randomUUID().toString());
		    
		    user1.setProvider(Providers.GOOGLE);
		    user1.setEnabled(true);
		    user1.setEmailVerified(true);
		    user1.setProviderUserId(user.getName());
		    user1.setRoleList(List.of(AppConstants.ROLE_USER));
		    user1.setAbout("This account is created using user");
		    
		    //save user in database
		   User user2= userRepo.findByEmail(email).orElse(null);
		   
		   if(user2==null) {
			   userRepo.save(user1);
		   }
		    */
		    //save user in database
		    User user2= userRepo.findByEmail(user.getEmail()).orElse(null);
		   
		   if(user2==null)
			   userRepo.save(user);
		   
		    new DefaultRedirectStrategy().sendRedirect(request, response,"/user/profile");
		
	}
	

}
