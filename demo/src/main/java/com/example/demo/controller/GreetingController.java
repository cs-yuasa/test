package com.example.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.GreetingUserDTO;
import com.example.demo.model.Greeting;
import com.example.demo.service.GreetingService;

@RestController
@RequestMapping("/")
public class GreetingController {
	
	private final GreetingService greetingService;
	
	public GreetingController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}
	
	@PostMapping
	public void createGreeting(@RequestParam String message) {
		greetingService.addGreeting(message);
	}
	
	@GetMapping("/{id}")
	public Greeting getGreeting(@PathVariable Long id) {
		return greetingService.getGreetingById(id);
	}
	
	@GetMapping
	public List<Greeting> getAllGreetings() {
		return greetingService.getAllGreetings();
	}
	
	@PostMapping("/update/{id}")
	public void updateGreeting(@PathVariable Long id, @RequestParam String message) {
		greetingService.updateMessage(id, message);
	}
	
	@PostMapping("/delete/{id}")
	public void deleteGreeting(@PathVariable Long id) {
		greetingService.deleteGreeting(id);
	}
	
	@GetMapping("/users")
    public List<GreetingUserDTO> getGreetingsWithUsers() {
        return greetingService.getAllGreetingsWithUsers();
    }
}
