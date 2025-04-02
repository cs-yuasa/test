package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.GreetingService;

@Controller
@RequestMapping("/")
public class DemoController {
	
	@Autowired
	private GreetingService greetingService;
	
	@GetMapping("/{id}")
	public String viewIndex(@PathVariable Long id, Model model) {
		model.addAttribute("message", greetingService.getMessageById(id));
		return "index";
	}
}
