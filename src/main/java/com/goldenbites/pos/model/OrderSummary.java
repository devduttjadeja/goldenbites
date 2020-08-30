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
	private Date itemId;

	@Field(name = "item_price")
	private Double itemPrice;
	
	@Field(name = "item_quantity")
	private Double itemQuantity;

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

	public Date getItemId() {
		return itemId;
	}

	public void setItemId(Date itemId) {
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

	@Override
	public String toString() {
		return "OrderSummary [orderSummaryId=" + orderSummaryId + ", orderId=" + orderId + ", itemId=" + itemId
				+ ", itemPrice=" + itemPrice + ", itemQuantity=" + itemQuantity + "]";
	}
	
}
