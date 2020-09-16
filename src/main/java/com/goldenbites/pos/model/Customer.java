package com.goldenbites.pos.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "customer")
public class Customer {
	
	@Id
	private String customerId;

	@Field(name = "customer_name")
	private String customerName;

	@Field(name = "customer_email")
	private String customerEmail;

	@Field(name = "customer_phone")
	private String customerPhone;
	
	@Field(name = "customer_code")
	private String customerCode;
	
	@Field(name = "customer_createddate")
	private Date customerCreatedDate;
	
	@Field(name = "customer_updateddate")
	private Date customerUpdatedDate;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public Date getCustomerCreatedDate() {
		return customerCreatedDate;
	}

	public void setCustomerCreatedDate(Date customerCreatedDate) {
		this.customerCreatedDate = customerCreatedDate;
	}

	public Date getCustomerUpdatedDate() {
		return customerUpdatedDate;
	}

	public void setCustomerUpdatedDate(Date customerUpdatedDate) {
		this.customerUpdatedDate = customerUpdatedDate;
	}

	@Override
	public String toString() {
		return "Customer [customerId=" + customerId + ", customerName=" + customerName + ", customerEmail="
				+ customerEmail + ", customerPhone=" + customerPhone + ", customerCode=" + customerCode
				+ ", customerCreatedDate=" + customerCreatedDate + ", customerUpdatedDate=" + customerUpdatedDate + "]";
	}
	
}
