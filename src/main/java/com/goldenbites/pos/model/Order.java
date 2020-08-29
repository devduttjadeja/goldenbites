package com.goldenbites.pos.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "orders")
public class Order {
	@Id
	private String id;

	private String date;
	private Integer total;

	public Order() {

	}

	public Order(String date, Integer total) {
		this.date = date;
		this.total = total;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", date=" + date + ", total=" + total + "]";
	}

	
}
