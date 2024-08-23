package com.bank.dreamBankService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "DecisionDetails")
public class DecisionDetails {
	
	@Id 
	private String decisionID;
	private String decisionName;
	
	public String getDecisionName() {
		return decisionName;
	}
	public void setDecisionName(String decisionName) {
		this.decisionName = decisionName;
	}
	public String getDecisionID() {
		return decisionID;
	}
	public void setDecisionID(String decisionID) {
		this.decisionID = decisionID;
	}
}
