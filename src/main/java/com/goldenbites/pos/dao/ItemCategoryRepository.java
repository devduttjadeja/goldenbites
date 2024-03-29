package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.ItemCategory;

	
public interface ItemCategoryRepository extends MongoRepository<ItemCategory, String> {

    ItemCategory findByItemCategoryId(String id);

	ItemCategory findByItemCategoryName(String itemCategoryName);
}
