package com.goldenbites.pos.controller;

import com.goldenbites.pos.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.goldenbites.pos.dao.UserRepository;
import com.goldenbites.pos.model.User;

import java.util.Calendar;
import java.util.Date;

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

		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		//user.setCustomerCreatedDate(now);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		user.setActive(1);
		userRepository.save(user);

		//model.addAttribute("user", new User());
		model.addAttribute("users", userRepository.findAll());
		return "User/viewUser";
	}

	@GetMapping("/home/viewUser")
	public String viewAllUser(Model model) {
		model.addAttribute("users", userRepository.findAll());
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
		user.setUserId(id);
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
		user.setActive(1);
//		Calendar calendar = Calendar.getInstance();
//		Date now = calendar.getTime();
//		customer.setCustomerCreatedDate(now);
		userRepository.save(user);
		model.addAttribute("users", userRepository.findAll());
		return "redirect:/home/viewUser";
	}

//	@PostMapping("/login")
//	public String greetingSubmit(@ModelAttribute User user, Model model) {
//
//		User userNew = userRepository.findByUserNameAndUserPasswordAndUserRole(user.getUserName(), user.getUserPassword(), user.getUserRole());
//
//		if(userNew !=  null) {
//			return "home";
//		}
//		else {
//			model.addAttribute("errorMessage", "Invalid Username or Password");
//			return "login";
//		}
//	}
	
}