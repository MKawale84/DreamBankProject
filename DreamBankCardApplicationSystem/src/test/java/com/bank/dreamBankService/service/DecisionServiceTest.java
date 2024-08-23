package com.bank.dreamBankService.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.bank.dreamBankService.model.DecisionDetails;
import com.bank.dreamBankService.model.ReportDetails;
import com.bank.dreamBankService.model.UserDetails;
import com.bank.dreamBankService.repository.DecisionDetailsRepo;
import com.bank.dreamBankService.repository.ReportDetailsRepo;

@SpringBootTest
class DecisionServiceTest {
	@Mock
	DecisionDetailsRepo decRepo;

	@Mock
	ReportDetailsRepo repRepo;

	@InjectMocks
	DecisionService decisionService;

	List<DecisionDetails> decisionList = new ArrayList<>();
	DecisionDetails decisionDetails = new DecisionDetails();
	ReportDetails reportDetails = new ReportDetails();
	UserDetails userDetails = new UserDetails();

	@Test
	void testGetDecision() throws InterruptedException {
		userDetails.setId(111);
		userDetails.setFirstName("Test1");
		userDetails.setLastName("Test1");
		userDetails.setAddress("Phoenix AZ");
		userDetails.setZipcode("85050");
		userDetails.setDob("12/12/1980");
		userDetails.setPhoneNumber("1231231234");
		userDetails.setSsn("12345");
		userDetails.setEquifaxScore(720);
		userDetails.setExperianScore(800);
		userDetails.setTransunionScore(780);

		decisionDetails.setDecisionID("111");
		decisionDetails.setDecisionName("Very Good");
		decisionList.add(decisionDetails);

		reportDetails.setAddress("Phoenix AZ");
		reportDetails.setDecision("Approved");
		reportDetails.setUserID(111);
		reportDetails.setZipcode("85050");

		Mockito.when(decRepo.findAll()).thenReturn(decisionList);
		Mockito.when(repRepo.save(reportDetails)).thenReturn(reportDetails);

		String result = decisionService.getDecision(userDetails);
		
		assertNotNull(result);
	}

	@Test
	void testIsBetween() {
		boolean value = decisionService.isBetween(720, 750, 1000);
		assertEquals(false, value);
	}

	@Test
	void testCheckDecisionWt() {
		int wt = decisionService.checkDecisionWt("Excellent");
		assertEquals(wt, 1);
		wt = decisionService.checkDecisionWt("Very Good");
		assertEquals(wt, 2);
		wt = decisionService.checkDecisionWt("Good");
		assertEquals(wt, 3);
		wt = decisionService.checkDecisionWt("Average");
		assertEquals(wt, 4);
		wt = decisionService.checkDecisionWt("Poor");
		assertEquals(wt, 5);

	}

}
