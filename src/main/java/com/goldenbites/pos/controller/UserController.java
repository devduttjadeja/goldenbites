package com.goldenbites.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.dao.UserRepository;
import com.goldenbites.pos.model.User;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/login")
	public String greetingForm(Model model) {
		model.addAttribute("user", new User());
		return "login";
	}

	@PostMapping("/login")
	public String greetingSubmit(@ModelAttribute User user, Model model) {
		
		User userNew = userRepository.findByUserNameAndUserPasswordAndUserRole(user.getUserName(), user.getUserPassword(), user.getUserRole());
		
		if(userNew !=  null) {
			return "home";
		}
		else {
			model.addAttribute("errorMessage", "Invalid Username or Password");
			return "login";
		}
			
			
	}
	
}