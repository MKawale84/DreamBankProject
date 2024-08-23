package com.bank.dreamBankService.service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bank.dreamBankService.model.UserDetails;

@Service
public class CreditScoreService {
	@Value("${experian.url}")
	private String experianURL;

	@Value("${equifax.url}")
	private String equifaxURL;

	@Value("${transunion.url}")
	private String transunionURL;

	@Autowired
	RestTemplate restemplate;
	
	public UserDetails getCreditScore(UserDetails user) {
		
		
		CompletableFuture<ResponseEntity<String>> experianFuture = CompletableFuture.supplyAsync(() -> {
			try {
				return restemplate.exchange("http://experianservice:8082/experian/" + user.getSsn(), HttpMethod.GET, null, String.class);
	        } catch (Exception e) {
	           return new ResponseEntity<String>("0", HttpStatus.SERVICE_UNAVAILABLE);
	        }
		});
		
				
		CompletableFuture<ResponseEntity<String>> equifaxFuture = CompletableFuture.supplyAsync(() -> {
			try {
				return restemplate.exchange("http://equifaxservice:8081/equifax/" + user.getSsn(), HttpMethod.GET, null, String.class);
	        } catch (Exception e) {
	           return new ResponseEntity<String>("0", HttpStatus.SERVICE_UNAVAILABLE);
	        }
		});
		
		CompletableFuture<ResponseEntity<String>> transunionFuture = CompletableFuture.supplyAsync(() -> {
			try {
				return restemplate.exchange("http://transunionservice:8083/transunion/" + user.getSsn(), HttpMethod.GET, null, String.class);
	        } catch (Exception e) {
	           return new ResponseEntity<String>("0", HttpStatus.SERVICE_UNAVAILABLE);
	        }
		});
		
		//Parallel execution of all Credit score services 
		CompletableFuture.allOf(experianFuture,equifaxFuture,transunionFuture).join();
		
		if(experianFuture.isDone()) {
			try {
				user.setExperianScore(Integer.valueOf(experianFuture.get().getBody()));
			} catch (Exception e) {
				user.setExperianScore(0);
			}
		}
		
		if(equifaxFuture.isDone()) {
			try {
				user.setEquifaxScore(Integer.valueOf(equifaxFuture.get().getBody()));
			} catch (Exception e) {
				user.setEquifaxScore(0);
			}
		}
		
		if(transunionFuture.isDone()) {
			try {
				user.setTransunionScore(Integer.valueOf(transunionFuture.get().getBody()));
			} catch (Exception e) {
				user.setTransunionScore(0);
			}
		}
	
		return user;
	}
}
