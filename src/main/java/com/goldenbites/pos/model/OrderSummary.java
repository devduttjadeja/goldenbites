package com.goldenbites.pos.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "ordersummary")
public class OrderSummary {

	@Id
	@Field(name = "order_summaryid")
	private String orderSummaryId;

	@Field(name = "order_id")
	private String orderId;

	@Field(name = "item_id")
	private String itemId;
	
	@Field(name = "item_name")
	private String itemName;

	@Field(name = "item_price")
	private Double itemPrice;
	
	@Field(name = "item_quantity")
	private Double itemQuantity;
	
	@Field(name = "item_totalprice")
	private Double itemTotalPrice;
	
	@Field(name = "item_totaltax1")
	private Double itemTotalTax1;
	
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Field(name = "item_totaltax2")
	private Double itemTotalTax2;
	
	public Double getItemTotalPrice() {
		return itemTotalPrice;
	}

	public void setItemTotalPrice(Double itemTotalPrice) {
		this.itemTotalPrice = itemTotalPrice;
	}

	public Double getItemTotalTax1() {
		return itemTotalTax1;
	}

	public void setItemTotalTax1(Double itemTotalTax1) {
		this.itemTotalTax1 = itemTotalTax1;
	}

	public Double getItemTotalTax2() {
		return itemTotalTax2;
	}

	public void setItemTotalTax2(Double itemTotalTax2) {
		this.itemTotalTax2 = itemTotalTax2;
	}

	public String getOrderSummaryId() {
		return orderSummaryId;
	}

	public void setOrderSummaryId(String orderSummaryId) {
		this.orderSummaryId = orderSummaryId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Double getItemQuantity() {
		return itemQuantity;
	}

	public void setItemQuantity(Double itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	
	
}
