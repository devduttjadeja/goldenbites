package com.goldenbites.pos.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.dao.UserRepository;
import com.goldenbites.pos.model.User;

@Controller
public class UserController {

	@Autowired
	UserRepository userRepository;


	@GetMapping("/home/registerUser")
	public String displayUserRegistrationForm(Model model) {
		model.addAttribute("user", new User());
		return "User/userRegistration";
	}

	@PostMapping("/home/registerUser")
	public String registerUser(@ModelAttribute User user, Model model) {

//		user.setUserName(user.getUserName().toLowerCase());
		user.setUserEmail(user.getUserEmail().toLowerCase());
		
		if((userRepository.findByUserName(user.getUserName()) != null) || 
				(userRepository.findByUserEmail(user.getUserEmail()) != null)) {
			model.addAttribute("user", new User());
			model.addAttribute("message", "User already exist.");
			return "User/userRegistration";
		}
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		//user.setCustomerCreatedDate(now);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		user.setActive(1);
		userRepository.save(user);

		//model.addAttribute("user", new User());
		
		return "redirect:/home/viewUser";
	}

	@GetMapping("/home/viewUser")
	public String viewAllUser(Model model) {
		List<User> users = userRepository.findAll();
		
		for (int i = 0; i<users.size();i++) {
			
			if(users.get(i).getUserName().equals("admin")) {
				users.remove(i);
				break;
			}
		}
		
		List<User> validUsers = new ArrayList<>();
		for (int i = 0; i<users.size();i++) {
			
			if(!users.get(i).getUserRole().equals("CUSTOMER")) {
				validUsers.add(users.get(i));
			}
		}
		
		model.addAttribute("users", validUsers);
		return "User/viewUser";
	}

	@GetMapping("/home/viewUser/userDelete/{id}")
	public String deleteUser(@PathVariable("id") String id, Model model) {
		userRepository.deleteById(id);
		model.addAttribute("users", userRepository.findAll());
		return "redirect:/home/viewUser";
	}


	@GetMapping("/home/viewUser/userEdit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
		User user = userRepository.findByUserId(id);
		model.addAttribute("user", user);
		return "User/updateUser";
	}

	@PostMapping("/home/viewUser/updateUser/{id}")
	public String updateUser(@PathVariable("id") String id, @ModelAttribute User user, BindingResult result,
								 Model model) {
		
//		user.setUserName(user.getUserName().toLowerCase());
		user.setUserEmail(user.getUserEmail().toLowerCase());
		
    	String name = userRepository.findByUserId(id).getUserName();
    	String email = userRepository.findByUserId(id).getUserEmail();
		
		if((!user.getUserName().equals(name) && (userRepository.findByUserName(user.getUserName()) != null)) ||
				(!user.getUserEmail().equals(email) && (userRepository.findByUserEmail(user.getUserEmail()) != null))) { 
			
    		return "redirect:/home/viewUser/userEdit/"+id;    	
    	}
		
		user.setUserId(id);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		user.setActive(1);
		userRepository.save(user);
		model.addAttribute("users", userRepository.findAll());
		return "redirect:/home/viewUser";
	}
}