package com.springboot.rbl.services;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.*;



@Service
public class RblPayoutService {
	
	 private final RblAuthService authService;

	    public RblPayoutService(RblAuthService authService) {
	        this.authService = authService;
	    }

	    @Value("${rbl.api.base.url}")
	    private String rblBaseUrl;

	    public Map<String, Object> doPayout(String accountNumber, double amount) {
	        String token = authService.getAccessToken();

	        String payoutUrl = rblBaseUrl + "/payout";

	        RestTemplate restTemplate = new RestTemplate();
	        HttpHeaders headers = new HttpHeaders();
	        headers.setBearerAuth(token);
	        headers.setContentType(MediaType.APPLICATION_JSON);

	        Map<String, Object> payload = new HashMap<>();
	        payload.put("accountNumber", accountNumber);
	        payload.put("amount", amount);
	        payload.put("remarks", "Test payout");

	        HttpEntity<Map<String, Object>> request = new HttpEntity<>(payload, headers);

	        ResponseEntity<Map> response = restTemplate.postForEntity(payoutUrl, request, Map.class);

	        return response.getBody();
	    }
	

}
