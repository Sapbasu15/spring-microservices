package com.example.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class CircuitBreakerController {
	
	Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	
	@GetMapping("/sample-api")
	@Retry(name = "sample-api", fallbackMethod = "handleFallback")
	public String sampleApi() {
		logger.info("starting Sample API call .. ");
		ResponseEntity<String> responseEntity = new RestTemplate()
				.getForEntity("http://localhost:8080/dummy-api", String.class);
		return responseEntity.getBody();
	}
	
	public String handleFallback(Exception exc) {
		return "Fallback Response";
	}
}
