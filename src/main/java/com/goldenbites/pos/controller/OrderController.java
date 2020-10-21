package com.goldenbites.pos.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goldenbites.pos.dao.CustomerRepository;
import com.goldenbites.pos.dao.OrderRepository;
import com.goldenbites.pos.model.Customer;

@Controller
public class OrderController {

	  @Autowired
	  OrderRepository orderRepository;

	  @Autowired
		CustomerRepository customerRepository;
	  
		@GetMapping("/home/viewOrders")
		public String viewOrders(Model model) {
			//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			//String currentPrincipalName = authentication.getName();
			//Customer customer = customerRepository.findByCustomerEmail(currentPrincipalName);
			
			
			model.addAttribute("orders", orderRepository.findAll());
			//model.addAttribute("orders", orderRepository.findByOrderCustomerCode(customer.getCustomerCode()));
			return "Order/viewOrders";
		}
	  
		
		@GetMapping("/home/viewOrders/datefilter")
		public String invoiceDisplayForOneOrder(@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate, Model model) {
	
			String[] startdates = startdate.split("-");
			String[] enddates = enddate.split("-");
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentPrincipalName = authentication.getName();
			
			Date OrderDateStart = new Date(Integer.parseInt(startdates[2])-1900,Integer.parseInt(startdates[1])-1,Integer.parseInt(startdates[0]));
			Date OrderDateEnd = new Date(Integer.parseInt(enddates[2])-1900,Integer.parseInt(enddates[1])-1,Integer.parseInt(enddates[0]));
			
			model.addAttribute("orders", orderRepository.findAllByOrderDateBetween(OrderDateStart, OrderDateEnd));
			return "Order/viewOrders";
		}
}
