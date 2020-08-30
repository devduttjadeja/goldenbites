package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

}
