package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.OrderSummary;

public interface OrderSummaryRepository extends MongoRepository<OrderSummary, String>{

}
