package com.bank.dreamBankService;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.bank.dreamBankService.model.DecisionDetails;
import com.bank.dreamBankService.model.ReportDetails;
import com.bank.dreamBankService.model.UserDetails;
import com.bank.dreamBankService.repository.DecisionDetailsRepo;
import com.bank.dreamBankService.repository.ReportDetailsRepo;
import com.bank.dreamBankService.repository.UserDetailsRepo;
import com.bank.dreamBankService.service.CreditScoreService;
import com.bank.dreamBankService.service.DecisionService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/dreamBankService")
public class DreamBankCardController {

	@Autowired
	UserDetailsRepo repo;

	@Autowired
	ReportDetailsRepo repRepo;

	@Autowired
	DecisionDetailsRepo decRepo;

	@Autowired
	DecisionService decisionService;

	@Autowired
	CreditScoreService creditService;

	@PostMapping("/decision")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "APPROVED : You are selected for Card Application/REJECTED : You are rejected for Card Application"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error"),
            @ApiResponse(responseCode = "503", description = "Service Unavailable")})
	@Operation(summary = "Decision API", description = "curl -X 'POST' \\\r\n"
			+ "  'http://localhost:8080/dreamBankService/decision' \\\r\n"
			+ "  -H 'accept: */*' \\\r\n"
			+ "  -H 'Content-Type: application/json' \\\r\n"
			+ "  -d '{ \"id\": 1015, \"zipcode\": \"85050\", \"ssn\": \"1015\", \"dob\": \"12/12/1980\", \"address\": \"Phoenix\", \"phoneNumber\": \"1231231234\", \"lastName\": \"Kawale\", \"firstName\": \"Mahesh\" }'")
	public ResponseEntity<Object> getDecison(@RequestBody UserDetails user){
		try {
			UserDetails userInfo = creditService.getCreditScore(user);
			// Save User details into MongoDB - COllection UserDetails
			repo.save(userInfo);
			// Call DecisionService for user for Approve/Reject decision
			String decision = decisionService.getDecision(userInfo);
			// Decision Details
			System.out.println(" ---------- MY DECISION ---------");
			System.out.println(decision);
			System.out.println("ExperianScore : " + userInfo.getExperianScore() + " EquifaxScore: " + userInfo.getEquifaxScore()
					+ " TransunionScore: " + userInfo.getTransunionScore());
			return new ResponseEntity<>(decision, HttpStatus.OK);
		}catch(InterruptedException ie) {
			return new ResponseEntity<>(ie.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
		}catch (CompletionException ce) {
			//If anyConnection refused while retriving credit score.
			return new ResponseEntity<>(ce.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
		}catch (Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getReport")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of Zipcodes with Approved count"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@Operation(summary = "List of Zipcodes which has more than 10 approved decisions", description = "curl -X 'GET' \\\r\n"
			+ "  'http://localhost:8080/dreamBankService/getReport' \\\r\n"
			+ "  -H 'accept: */*'")
	public ResponseEntity<Object> getReportDetails() {
		try {
			List<ReportDetails> reportList = repRepo.findAll();
			List<ReportDetails> approvedReportList = reportList.stream()
					.filter(s -> s.getDecision().equalsIgnoreCase("Approved")).collect(Collectors.toList());

			Map<Object, Long> dups = approvedReportList.stream()
					.collect(Collectors.groupingBy(s -> s.getZipcode(), Collectors.counting()));

			List<Entry<Object, Long>> filteredList = dups.entrySet().stream().filter(s -> s.getValue() > 10)
					.collect(Collectors.toList());
			return new ResponseEntity<>(filteredList, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	@PostMapping("/updateDecisionFlag")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Record Updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@Operation(summary = "Stakeholders Predefined criteria for Approval", description = "curl -X 'POST' \\\r\n"
			+ "  'http://localhost:8080/dreamBankService/updateDecisionFlag' \\\r\n"
			+ "  -H 'accept: */*' \\\r\n"
			+ "  -H 'Content-Type: application/json' \\\r\n"
			+ "  -d '{\r\n"
			+ "  \"decisionID\": 1,\r\n"
			+ "  \"decisionName\": \"Good\"\r\n"
			+ "}'")
	public ResponseEntity<Object> updateDecisionDetails(@RequestBody DecisionDetails dec) {
		try {
			decRepo.save(dec);
			return new ResponseEntity<>("Record Updated successfully", HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/getDecision")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card Approved/Reject Decision made from this static data"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@Operation(summary = "Get Stakeholders Predefined decision details", description = "curl -X 'GET' \\\r\n"
			+ "  'http://localhost:8080/dreamBankService/getDecision' \\\r\n"
			+ "  -H 'accept: */*'")
	public ResponseEntity<Object> getDecisionDetails() {
		try {
			List<DecisionDetails> decisionDetails = decRepo.findAll();
			return new ResponseEntity<>(decisionDetails, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/getUserDetails")
	@ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Card Approved/Reject Decision made from this satic data"),
            @ApiResponse(responseCode = "400", description = "Bad Request"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")})
	@Operation(summary = "Get All User details", description = "curl -X 'GET' \\\r\n"
			+ "  'http://localhost:8080/dreamBankService/getUserDetails' \\\r\n"
			+ "  -H 'accept: */*'")
	public ResponseEntity<Object> getUserDetails() {
		try {
			List<UserDetails> userDetails = repo.findAll();
			return new ResponseEntity<>(userDetails, HttpStatus.OK);
			
		}catch(Exception e) {
			return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
