package com.bank.dreamBankService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.jta.UserTransactionAdapter;

import com.bank.dreamBankService.model.DecisionDetails;
import com.bank.dreamBankService.model.ReportDetails;
import com.bank.dreamBankService.model.UserDetails;
import com.bank.dreamBankService.repository.DecisionDetailsRepo;
import com.bank.dreamBankService.repository.ReportDetailsRepo;

@Service
public class DecisionService {
	
	@Autowired
	private DecisionDetailsRepo decRepo;
	
	@Autowired
	private ReportDetailsRepo repRepo;
	
	public String getDecision(UserDetails user) throws InterruptedException {
		
		String creditScore = "Equifax Score : " + user.getEquifaxScore() + " Experian Score : "+ user.getExperianScore() + " Transunion Score : " + user.getTransunionScore();
		String out = "Rejected : No Credit score available for this User";
		List<Integer> userCreditScoreList = new ArrayList<Integer>();
		ReportDetails reportDetails = new ReportDetails();
		reportDetails.setDecision("Rejected");
		reportDetails.setAddress(user.getAddress());
		reportDetails.setUserID(user.getId());
		reportDetails.setZipcode(user.getZipcode());
		if(user.getEquifaxScore() !=0)
			userCreditScoreList.add(user.getEquifaxScore());
		if(user.getExperianScore() != 0)
			userCreditScoreList.add(user.getExperianScore());
		if(user.getTransunionScore() !=0)
			userCreditScoreList.add(user.getTransunionScore());
		
		if(!userCreditScoreList.isEmpty()) {
			Integer lowestCreditSCore = userCreditScoreList.stream().mapToInt(v -> v).min()
					.orElseThrow(NoSuchElementException::new);
			List<DecisionDetails> decisionList = decRepo.findAll();
			String finalDecision = decisionList.stream().findFirst().get().getDecisionName();
			int staticDecision = checkDecisionWt(finalDecision);
			int decisionwt = 0;
			if (isBetween(lowestCreditSCore, 750, 1000)) {
				decisionwt = 1;
			} else if (isBetween(lowestCreditSCore, 700, 749)) {
				decisionwt = 2;
			} else if (isBetween(lowestCreditSCore, 650, 699)) {
				decisionwt = 3;
			} else if (isBetween(lowestCreditSCore, 600, 649)) {
				decisionwt = 4;
			} else {
				decisionwt = 5;
			}
			if (staticDecision >= decisionwt) {
				reportDetails.setDecision("Approved");
				out = "APPROVED : You are selected for Card Application";
			} else {
				reportDetails.setDecision("Rejected");
				out = "REJECTED : You are rejected for Card Application";
			}
		}
		repRepo.save(reportDetails);
		return creditScore + " \n "+ out;
	}

	public boolean isBetween(int x, int lower, int upper) {
		return lower <= x && x <= upper;
	}

	public int checkDecisionWt(String finalDecision) {
		int decisionwt = 0;
		if (finalDecision.equalsIgnoreCase("Excellent")) {
			decisionwt = 1;
		} else if (finalDecision.equalsIgnoreCase("Very Good")) {
			decisionwt = 2;
		} else if (finalDecision.equalsIgnoreCase("Good")) {
			decisionwt = 3;
		} else if (finalDecision.equalsIgnoreCase("Average")) {
			decisionwt = 4;
		} else {
			decisionwt = 5;
		}
		return decisionwt;
	}
}
