package com.goldenbites.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.goldenbites.pos.dao.OrderRepository;

@Controller
public class OrderController {

	  @Autowired
	  OrderRepository orderRepository;

		@GetMapping("/viewOrders")
		public String viewOrders(Model model) {
			model.addAttribute("orders", orderRepository.findAll());
			return "Order/viewOrders";
		}
	  
	
}
