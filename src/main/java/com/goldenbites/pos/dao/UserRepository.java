package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.User;

public interface UserRepository extends MongoRepository<User, String> {

	public User findByUserNameAndUserPasswordAndUserRole(String userName, String userPassword, String userRole);
	
}
