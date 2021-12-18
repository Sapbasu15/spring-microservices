package com.example.currencyexchangeservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;

@RestController
public class CircuitBreakerController {
	
	Logger logger = LoggerFactory.getLogger(CircuitBreakerController.class);
	RestTemplate restTemplate = new RestTemplate();
	
	@GetMapping("/sample-api")
//	@Retry(name = "sample-api", fallbackMethod = "handleFallback")
	@CircuitBreaker(name = "default", fallbackMethod = "handleFallback")
	@RateLimiter(name = "default")
	@Bulkhead(name = "default")
	public String sampleApi() {
		logger.info("starting Sample API call .. ");
		ResponseEntity<String> responseEntity = restTemplate
				.getForEntity("http://localhost:8080/dummy-api", String.class);
		return responseEntity.getBody();
	}
	
	public String handleFallback(Exception exc) {
		return "Fallback Response";
	}
}
