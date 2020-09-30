package com.goldenbites.pos.controller;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.goldenbites.pos.dao.CustomerRepository;
import com.goldenbites.pos.model.Customer;
import com.goldenbites.pos.model.Item;

@Controller
public class customerController {

	@Autowired
	CustomerRepository customerRepository;

	@GetMapping("/home/registerCustomer")
	public String displayCustomerRegistrationForm(Model model) {
		model.addAttribute("customer", new Customer());
		return "Customer/customerRegistration";
	}
	
	@PostMapping("/home/registerCustomer")
	public String registerCustomer(@ModelAttribute Customer customer,Model model) {
		
		Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
	    customer.setCustomerCreatedDate(now);
		customerRepository.save(customer);
		model.addAttribute("customer", new Customer());
		return "Customer/customerRegistration";
	}
	
	
	@GetMapping("/home/viewCustomer")
	public String viewAllCustomer(Model model) {
		model.addAttribute("customers", customerRepository.findAll());
		return "Customer/viewCustomer";
	}
	
	@GetMapping("/home/viewCustomer/customerDelete/{id}")
	public String deleteCustomer(@PathVariable("id") String id, Model model) {
		customerRepository.deleteById(id);
	    model.addAttribute("customers", customerRepository.findAll());
	    return "redirect:/home/viewCustomer";
	}
	
	
	@GetMapping("/home/viewCustomer/customerEdit/{id}")
	public String showUpdateForm(@PathVariable("id") String id, Model model) {
	    Customer customer = customerRepository.findByCustomerId(id);
	    model.addAttribute("customer", customer);
	    return "Customer/updateCustomer";
	}
	
	
	@PostMapping("/home/viewCustomer/updateCustomer/{id}")
	public String updateCustomer(@PathVariable("id") String id, @ModelAttribute Customer customer, 
	  BindingResult result, Model model) {     
		customer.setCustomerId(id);
	    Calendar calendar = Calendar.getInstance();
		Date now = calendar.getTime();
		customer.setCustomerCreatedDate(now);
		customerRepository.save(customer);
	    model.addAttribute("customers", customerRepository.findAll());
	    return "redirect:/home/viewCustomer";
	}

}
