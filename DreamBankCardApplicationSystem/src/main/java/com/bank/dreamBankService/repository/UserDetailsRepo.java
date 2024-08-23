package com.bank.dreamBankService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bank.dreamBankService.model.UserDetails;


public interface UserDetailsRepo extends MongoRepository<UserDetails, Integer>{

}
