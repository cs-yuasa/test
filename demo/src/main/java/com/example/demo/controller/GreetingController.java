package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.service.GreetingService;

@Controller
@RequestMapping("/")
public class GreetingController {
	
	private final GreetingService greetingService;
	
	public GreetingController(GreetingService greetingService) {
		this.greetingService = greetingService;
	}
	
	@PostMapping("/create")
	public String createGreetingAndUser(
			@RequestParam String name, 
			@RequestParam String message, 
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {
		greetingService.addNameAndMessage(name, message);
		redirectAttributes.addFlashAttribute("successMessage", "作成が完了しました！");
		String referer = request.getHeader("Referer");
		return "redirect:" + (referer != null ? referer : "/");
	}
	
	@GetMapping
	public String getGreetingsWithUsers(Model model) {
		model.addAttribute("users", greetingService.getAllName());
		model.addAttribute("greetings", greetingService.getAllGreetingsWithUsers());
		return "index";
    }
	
	@PostMapping("/update/{id}")
	public String updateMessageAndName(
			@PathVariable Long id, 
			@RequestParam String message, 
			@RequestParam String memo, 
			@RequestParam String name, 
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {
		greetingService.updateGreetingAndUser(id, message, memo, name);
		redirectAttributes.addFlashAttribute("successMessage", "更新が完了しました！");
		String referer = request.getHeader("Referer");
		return "redirect:" + (referer != null ? referer : "/");
	}
	
	@PostMapping("/delete/{id}")
	public String deleteGreeting(
			@PathVariable Long id, 
			RedirectAttributes redirectAttributes, 
			HttpServletRequest request) {
		greetingService.deleteGreeting(id);
		redirectAttributes.addFlashAttribute("successMessage", "削除が完了しました！");
		String referer = request.getHeader("Referer");
		return "redirect:" + (referer != null ? referer : "/");
	}
}
