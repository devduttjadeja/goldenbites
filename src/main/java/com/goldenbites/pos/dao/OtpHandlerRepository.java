package com.goldenbites.pos.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.goldenbites.pos.model.OtpHandler;

@Repository
public interface OtpHandlerRepository extends MongoRepository<OtpHandler, String>{

	OtpHandler findByUserEmail(String userEmail);
}
