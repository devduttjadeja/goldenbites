package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.goldenbites.pos.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	public User findByUserName(String userName);
	public User findByUserNameAndUserPasswordAndUserRole(String userName, String userPassword, String userRole);

    User findByUserId(String id);
	public void deleteByUserName(String customerEmail);
}
