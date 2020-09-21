package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.ItemCategory;

	
public interface ItemCategoryRepository extends MongoRepository<ItemCategory, String> {

}
