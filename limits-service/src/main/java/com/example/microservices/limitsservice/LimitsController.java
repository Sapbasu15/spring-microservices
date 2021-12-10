package com.example.microservices.limitsservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.microservices.limitsservice.beans.Limits;
import com.example.microservices.limitsservice.configurations.LimitsConfigurations;

@RestController
public class LimitsController {
	
	@Autowired
	LimitsConfigurations limitsConfigurations;

	@GetMapping("/limits")
	public Limits retrieveLimits() {
		return new Limits(limitsConfigurations.getMinimum(), 
				limitsConfigurations.getMaximum());
	}
}
