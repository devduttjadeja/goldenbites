package com.goldenbites.pos.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.dao.CustomerRepository;
import com.goldenbites.pos.model.Customer;

@Controller
public class customerController {

	@Autowired
	CustomerRepository customerRepository;

	@GetMapping("/registerCustomer")
	public String displayCustomerRegistrationForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "Customer/customerRegistration";
	}
	
	@PostMapping("/registerCustomer")
	public String registerCustomer(@ModelAttribute Customer customer,Model model) {
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
	    customer.setCustomerCreatedDate(now);
		customerRepository.save(customer);
		model.addAttribute("customer", new Customer());
		return "Customer/customerRegistration";
	}
	
	
	@GetMapping("/viewCustomer")
	public String viewAllCustomer(Model model) {
		model.addAttribute("customers", customerRepository.findAll());
		return "Customer/viewCustomer";
	}

}
