package com.springboot.rbl.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;

@Service
public class RblAuthService {
	
	 @Value("${rbl.client.id}")
	    private String clientId;

	    @Value("${rbl.client.secret}")
	    private String clientSecret;

	    @Value("${rbl.auth.url}")
	    private String authUrl;

	    public String getAccessToken() {
	        RestTemplate restTemplate = new RestTemplate();

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        Map<String, String> body = new HashMap<>();
	        body.put("grant_type", "client_credentials");
	        body.put("client_id", clientId);
	        body.put("client_secret", clientSecret);

	        HttpEntity<Map<String, String>> request = new HttpEntity<>(body, headers);

	        ResponseEntity<Map> response = restTemplate.postForEntity(authUrl, request, Map.class);

	        if (response.getStatusCode() == HttpStatus.OK) {
	            return (String) response.getBody().get("access_token");
	        } else {
	            throw new RuntimeException("Failed to get access token: " + response.getStatusCode());
	        }
	    }

}
