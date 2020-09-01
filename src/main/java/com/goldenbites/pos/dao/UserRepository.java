package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	User findByuserName(String userName);

}
