package com.bank.dreamBankService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bank.dreamBankService.model.DecisionDetails;

public interface DecisionDetailsRepo extends MongoRepository<DecisionDetails, String>{

}
