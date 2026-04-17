package com.springboot.rbl.scheduler;

import org.springframework.scheduling.annotation.Scheduled;

public class CkycRetryScheduler {

	
	@Scheduled(cron = "0 0 */6 * * *")
	public void retryFailed() {
	   // retry logic
	}
}
