package com.goldenbites.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.model.User;

@Controller
public class UserController {

	@GetMapping("/user")
	public String greetingForm(Model model) {
		model.addAttribute("user", new User());
		return "user";
	}

	@PostMapping("/user")
	public String greetingSubmit(@ModelAttribute User user, Model model) {
		model.addAttribute("user", user);
		return "result";
	}

}