package com.goldenbites.pos.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "itemcategory")
public class ItemCategory {

	@Id
	private String itemCategoryId;

	@Field(name = "item_categoryname")
	private String itemCategoryName;
	
	@Field(name = "item_category_createddate")
	private Date itemCategoryCreatedDate;
	
	@Field(name = "item_category_updateddate")
	private Date itemCategoryUpdatedDate;

	public String getItemCategoryId() {
		return itemCategoryId;
	}

	public void setItemCategoryId(String itemCategoryId) {
		this.itemCategoryId = itemCategoryId;
	}

	public String getItemCategoryName() {
		return itemCategoryName;
	}

	public void setItemCategoryName(String itemCategoryName) {
		this.itemCategoryName = itemCategoryName;
	}

	public Date getItemCategoryCreatedDate() {
		return itemCategoryCreatedDate;
	}

	public void setItemCategoryCreatedDate(Date itemCategoryCreatedDate) {
		this.itemCategoryCreatedDate = itemCategoryCreatedDate;
	}

	public Date getItemCategoryUpdatedDate() {
		return itemCategoryUpdatedDate;
	}

	public void setItemCategoryUpdatedDate(Date itemCategoryUpdatedDate) {
		this.itemCategoryUpdatedDate = itemCategoryUpdatedDate;
	}

	@Override
	public String toString() {
		return "ItemCategory [itemCategoryId=" + itemCategoryId + ", itemCategoryName=" + itemCategoryName
				+ ", itemCategoryCreatedDate=" + itemCategoryCreatedDate + ", itemCategoryUpdatedDate="
				+ itemCategoryUpdatedDate + "]";
	}

	
}
