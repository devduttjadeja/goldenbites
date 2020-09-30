package com.goldenbites.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DemoController {

	@RequestMapping("/")
	public String displayLoginPage() {
		return "login";
	}
	
	@RequestMapping("/registerStudent")
	public String studentRegistration() {
		return "registerStudent";
	}
	
}
