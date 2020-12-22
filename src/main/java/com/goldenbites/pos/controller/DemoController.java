package com.goldenbites.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

	@RequestMapping("/")
	public String displayLoginPage() {
		return "index";
	}

	@GetMapping("/login")
	public String login(Model model) {
		return "login";
	}
	
}
