package com.bank.dreamBankService.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bank.dreamBankService.model.ReportDetails;

public interface ReportDetailsRepo extends MongoRepository<ReportDetails, Integer>{

}
