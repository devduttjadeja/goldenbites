package com.goldenbites.pos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.goldenbites.pos.dao.OrderRepository;

@RestController
public class OrderController {

	  @Autowired
	  OrderRepository orderRepository;

//	  @PostMapping("/order")
//	  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
//	    try {
//	      Order _order = orderRepository.save(new Order(order.getDate(), order.getTotal()));
//	      return new ResponseEntity<>(_order, HttpStatus.CREATED);
//	    } catch (Exception e) {
//	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	  }
	
}
