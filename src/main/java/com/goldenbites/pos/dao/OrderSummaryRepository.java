package com.goldenbites.pos.dao;

import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.OrderSummary;

public interface OrderSummaryRepository extends MongoRepository<OrderSummary, String>{

	ArrayList<OrderSummary> findAllByOrderId(String orderId);

}
