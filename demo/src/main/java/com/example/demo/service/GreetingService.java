package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Greeting;
import com.example.demo.repository.GreetingRepository;

@Service
public class GreetingService {
	
	@Autowired
	private GreetingRepository greetingRepository;
	
	public String getMessageById(Long id) {
		Greeting greeting = greetingRepository.findById(id).orElse(null);
		if (greeting != null) {
			return greeting.getMessage();
		} else {
			return "Message not found";
		}
	}
}
