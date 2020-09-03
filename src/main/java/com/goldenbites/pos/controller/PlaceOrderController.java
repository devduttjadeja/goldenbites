package com.goldenbites.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goldenbites.pos.dao.UserRepository;

@Controller
public class PlaceOrderController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/home")
	public String homePage(Model model) {
		//model.addAttribute("user", new User());
		return "home";
	}
	
	@GetMapping("/categories")
	public String categoryDisplay(Model model) {
		//model.addAttribute("user", new User());
		return "Place Order/categories";
	}

	@GetMapping("/items")
	public String itemDisplay(Model model) {
		//model.addAttribute("user", new User());
		return "Place Order/items";
	}
	
	@GetMapping("/ordersummary")
	public String orderSummaryDisplay(Model model) {
		//model.addAttribute("user", new User());
		return "Place Order/orderSummary";
	}
	
	@GetMapping("/customerselection")
	public String customerSelection(Model model) {
		//model.addAttribute("user", new User());
		return "Place Order/customerSelection";
	}
	
	@GetMapping("/payment")
	public String paymentOptions(Model model) {
		//model.addAttribute("user", new User());
		return "Place Order/paymentOptions";
	}
	
	@GetMapping("/exit")
	public String exitDisplay(Model model) {
		//model.addAttribute("user", new User());
		return "Place Order/exit";
	}

}