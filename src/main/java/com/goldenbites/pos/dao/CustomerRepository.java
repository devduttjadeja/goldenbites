package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

	Customer findByCustomerId(String id);
}
