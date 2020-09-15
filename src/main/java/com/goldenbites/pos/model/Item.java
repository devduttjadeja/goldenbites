package com.goldenbites.pos.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


@Document(collection = "item")
public class Item {

	@Id
	private String itemId;

	@Field(name = "item_name")
	private String itemName;

	@Field(name = "item_category")
	private String itemCategory;

	@Field(name = "item_price")
	private Double itemPrice;
	
	@Field(name = "item_istaxable")
	private String itemIsTaxable;

	@Field(name = "item_tax1")
	private Double itemTax1;
	
	@Field(name = "item_tax2")
	private Double itemTax2;
	
	@Field(name = "item_taxtotal")
	private Double itemTaxTotal;
	
	@Field(name = "item_createorupdatedate")
	private Date itemCreateOrUpdateDate;
	
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemCategory() {
		return itemCategory;
	}

	public void setItemCategory(String itemCategory) {
		this.itemCategory = itemCategory;
	}

	public Double getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}

	public Double getItemTax1() {
		return itemTax1;
	}

	public void setItemTax1(Double itemTax1) {
		this.itemTax1 = itemTax1;
	}

	public Date getItemCreateOrUpdateDate() {
		return itemCreateOrUpdateDate;
	}

	public void setItemCreateOrUpdateDate(Date itemCreateOrUpdateDate) {
		this.itemCreateOrUpdateDate = itemCreateOrUpdateDate;
	}

	public Double getItemTax2() {
		return itemTax2;
	}

	public void setItemTax2(Double itemTax2) {
		this.itemTax2 = itemTax2;
	}

	public Double getItemTaxTotal() {
		return itemTaxTotal;
	}

	public void setItemTaxTotal(Double itemTaxTotal) {
		this.itemTaxTotal = itemTaxTotal;
	}
	
	public String getItemIsTaxable() {
		return itemIsTaxable;
	}

	public void setItemIsTaxable(String itemIsTaxable) {
		this.itemIsTaxable = itemIsTaxable;
	}

	

}
