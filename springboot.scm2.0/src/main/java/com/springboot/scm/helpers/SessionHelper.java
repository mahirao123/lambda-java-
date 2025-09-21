package com.springboot.scm.helpers;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
	
	public void removeMessage() {
	    try {
	        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	        if (attr != null) {
	            HttpSession session = attr.getRequest().getSession(false);
	            if (session != null) {
	                session.removeAttribute("message");
	            }
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

}
