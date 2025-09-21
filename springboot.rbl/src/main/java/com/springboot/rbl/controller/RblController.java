package com.springboot.rbl.controller;

import org.springframework.web.bind.annotation.*;

import com.springboot.rbl.services.RblPayoutService;

import java.util.Map;

@RestController
@RequestMapping("/api/rbl")

public class RblController {
		
	 private final RblPayoutService payoutService;

	    public RblController(RblPayoutService payoutService) {
	        this.payoutService = payoutService;
	    }

	    @PostMapping("/payout")
	    public Map<String, Object> payout(@RequestParam String accountNumber, @RequestParam double amount) {
	        return payoutService.doPayout(accountNumber, amount);
	    }
	
}
