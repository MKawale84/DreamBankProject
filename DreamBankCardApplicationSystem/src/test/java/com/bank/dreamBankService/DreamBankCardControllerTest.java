package com.bank.dreamBankService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import com.bank.dreamBankService.model.DecisionDetails;
import com.bank.dreamBankService.model.ReportDetails;
import com.bank.dreamBankService.model.UserDetails;
import com.bank.dreamBankService.repository.DecisionDetailsRepo;
import com.bank.dreamBankService.repository.ReportDetailsRepo;
import com.bank.dreamBankService.repository.UserDetailsRepo;
import com.bank.dreamBankService.service.CreditScoreService;
import com.bank.dreamBankService.service.DecisionService;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class DreamBankCardControllerTest {

	@Mock
	ReportDetailsRepo repRepo;
	
	@Mock
	UserDetailsRepo repo;
	
	@Mock
	DecisionDetailsRepo decRepo;
	
	@InjectMocks
	DreamBankCardController controller;
	
	
	@InjectMocks
	CreditScoreService creditService;
	
	@InjectMocks
	DecisionService decisionService;	
	
	List<ReportDetails> reportList = new ArrayList<>();
	ReportDetails reportDetails = new ReportDetails();
	UserDetails userDetails = new UserDetails();
	DecisionDetails decisionDetails = new DecisionDetails();
	List<UserDetails> userDetailsList = new ArrayList<>();
	
	@Test
	public void testGetReportDetails() {
		reportDetails.setDecision("Approved");
		reportDetails.setZipcode("85050");
		reportList.add(reportDetails);
		Mockito.when(repRepo.findAll()).thenReturn(reportList);
		ResponseEntity<Object>  result = controller.getReportDetails();
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void testUpdateDecisionDetails() {
		
		decisionDetails.setDecisionID("111");
		decisionDetails.setDecisionName("Very Good");
		
		Mockito.when(decRepo.save(decisionDetails)).thenReturn(decisionDetails);
		ResponseEntity<Object> details = controller.updateDecisionDetails(decisionDetails);
		assertEquals(details.getStatusCode(), HttpStatus.OK);
		
	}

	@Test
	void testGetDecisionDetails() {
		List<DecisionDetails> details = new ArrayList<DecisionDetails>();
		Mockito.when(decRepo.findAll()).thenReturn(details);
		ResponseEntity<Object> result  = controller.getDecisionDetails();
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
	
	@Test
	void testGetUserDetails() {
		Mockito.when(repo.findAll()).thenReturn(userDetailsList);
		ResponseEntity<Object> result  = controller.getUserDetails();
		assertEquals(result.getStatusCode(), HttpStatus.OK);
	}
}
