package com.goldenbites.pos.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "order")
public class Order {
	
	@Id
	private String orderId;

	@Field(name = "order_date")
	private Date orderDate;
	
	@Field(name = "order_paymenttype")
	private String orderPaymentType;

	@Field(name = "order_total")
	private Double orderTotal;
	
	@Field(name = "order_tax1")
	private Double orderTax1;
	
	@Field(name = "order_tax2")
	private Double orderTax2;
	
	@Field(name = "order_taxtotal")
	private Double orderTaxTotal;
	
	@Field(name = "order_finaltotal")
	private Double orderFinalTotal;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderPaymentType() {
		return orderPaymentType;
	}

	public void setOrderPaymentType(String orderPaymentType) {
		this.orderPaymentType = orderPaymentType;
	}

	public Double getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(Double orderTotal) {
		this.orderTotal = orderTotal;
	}

	public Double getOrderTax1() {
		return orderTax1;
	}

	public void setOrderTax1(Double orderTax1) {
		this.orderTax1 = orderTax1;
	}

	public Double getOrderTax2() {
		return orderTax2;
	}

	public void setOrderTax2(Double orderTax2) {
		this.orderTax2 = orderTax2;
	}

	public Double getOrderTaxTotal() {
		return orderTaxTotal;
	}

	public void setOrderTaxTotal(Double orderTaxTotal) {
		this.orderTaxTotal = orderTaxTotal;
	}

	public Double getOrderFinalTotal() {
		return orderFinalTotal;
	}

	public void setOrderFinalTotal(Double orderFinalTotal) {
		this.orderFinalTotal = orderFinalTotal;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate
				+ ", orderPaymentType=" + orderPaymentType + ", orderTotal=" + orderTotal + ", orderTax1=" + orderTax1
				+ ", orderTax2=" + orderTax2 + ", orderTaxTotal=" + orderTaxTotal + ", orderFinalTotal="
				+ orderFinalTotal + "]";
	}

}
