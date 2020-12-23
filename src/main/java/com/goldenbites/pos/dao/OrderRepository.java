package com.goldenbites.pos.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.goldenbites.pos.model.Order;

public interface OrderRepository extends MongoRepository<Order, String> {

	Order findByOrderId(String orderId);

	Order findFirstByOrderByOrderDateDesc();

	List<Order> findAllByOrderDateBetween(Date OrderDateStart, Date OrderDateEnd);

	List<Order> findAllByOrderDate(Date OrderDateStart);

	public void deleteByOrderCustomerCode(String customerCode);
		
}
