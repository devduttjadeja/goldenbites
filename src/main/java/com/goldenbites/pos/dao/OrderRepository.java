package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

	Order findByOrderId(String orderId);
  
}

