package com.goldenbites.pos.model;

import java.util.ArrayList;

public class OrderCreation {

	public ArrayList<OrderSummary> orderSummaryList;
	
	public OrderCreation() {
		orderSummaryList = new ArrayList<OrderSummary>();
	}

	public void addOrderSummary(OrderSummary orderSummary) {
		this.orderSummaryList.add(orderSummary);
	}

	public ArrayList<OrderSummary> getOrderSummaryList() {
		return orderSummaryList;
	}

	public void setOrderSummaryList(ArrayList<OrderSummary> orderSummaryList) {
		this.orderSummaryList = orderSummaryList;
	}
}