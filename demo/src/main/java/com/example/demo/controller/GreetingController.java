package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.GreetingService;

@Controller
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
	public String getGreeting(@PathVariable Long id, Model model) {
		model.addAttribute("greetings", greetingService.getGreetingById(id));
		return "index";
	}
	
	@GetMapping
	public String getAllGreetings(Model model) {
		model.addAttribute("greetings", greetingService.getAllGreetings());
		return "index";
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
    public String getGreetingsWithUsers(Model model) {
		model.addAttribute("greetingsWithUsers", greetingService.getAllGreetingsWithUsers());
        return "index";
    }
}
