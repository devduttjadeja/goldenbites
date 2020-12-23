package com.goldenbites.pos.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goldenbites.pos.dao.CustomerRepository;
import com.goldenbites.pos.dao.OrderRepository;
import com.goldenbites.pos.model.Order;

@Controller
public class OrderController {

	  @Autowired
	  OrderRepository orderRepository;

	  @Autowired
	  CustomerRepository customerRepository;
	  
		@GetMapping("/home/viewOrders")
		public String viewOrders(Model model) {
			List<Order> orders= orderRepository.findAll();
			
			
			List<Order> validOrders = new ArrayList<Order>();
			for(int i=0; i<orders.size(); i++) {
				Order order = orders.get(i);
				if(order.getOrderCustomerCode() != null) {
					validOrders.add(order);
				}
			}
			
			model.addAttribute("orders", validOrders);
			return "Order/viewOrders";
		}
	  

		@GetMapping("/home/viewOrders/datefilter")
		public String invoiceDisplayForOneOrder(@RequestParam("startdate") String startdate,@RequestParam("enddate") String enddate, Model model) {
	
			if(startdate.equals(enddate)) {
				String[] startdates = startdate.split("-");
				Date OrderDateStart = new Date(Integer.parseInt(startdates[2])-1900,Integer.parseInt(startdates[1])-1,Integer.parseInt(startdates[0]));
				Date OrderDateEnd = new Date(Integer.parseInt(startdates[2])-1900,Integer.parseInt(startdates[1])-1,Integer.parseInt(startdates[0])+1);
				model.addAttribute("orders", orderRepository.findAllByOrderDateBetween(OrderDateStart, OrderDateEnd));
				return "Order/viewFilteredOrders";
			}
			
			String[] startdates = startdate.split("-");
			String[] enddates = enddate.split("-");
			
			Date OrderDateStart = new Date(Integer.parseInt(startdates[2])-1900,Integer.parseInt(startdates[1])-1,Integer.parseInt(startdates[0]));
			Date OrderDateEnd = new Date(Integer.parseInt(enddates[2])-1900,Integer.parseInt(enddates[1])-1,Integer.parseInt(enddates[0])+1);
			
			model.addAttribute("orders", orderRepository.findAllByOrderDateBetween(OrderDateStart, OrderDateEnd));
			return "Order/viewFilteredOrders";
		}
}
