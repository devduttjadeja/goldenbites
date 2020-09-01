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
	public String greetingSubmit(@ModelAttribute User user) {
		
		User userNew = userRepository.findByuserName(user.getUserName());

		
		if(userNew !=  null && userNew.getUserPassword().equals(user.getUserPassword()))
			return "index";
		else
			return "login";
			
	}

}