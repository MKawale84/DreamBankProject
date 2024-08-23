package com.bank.dreamBankService.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.bank.dreamBankService.model.UserDetails;

@SpringBootTest
class CreditScoreServiceTest {
	
	@Mock
	RestTemplate restemplate;
	
	@InjectMocks
	CreditScoreService creditService;

	@Test
	void testGetCreditScore() throws InterruptedException {
		//CreditScoreService creditService = new CreditScoreService();
		UserDetails userDetails = new UserDetails();
		userDetails.setId(111);
		userDetails.setFirstName("Test1");
		userDetails.setLastName("Test1");
		userDetails.setAddress("Phoenix AZ");
		userDetails.setZipcode("85050");
		userDetails.setDob("12/12/1980");
		userDetails.setPhoneNumber("1231231234");
		userDetails.setSsn("12345");
		
		HttpHeaders header = new HttpHeaders();
		header.setContentType(MediaType.APPLICATION_JSON);
		
		ResponseEntity<String> creditScoreResult = new ResponseEntity<>(
			    "720",
			    header, 
			    HttpStatus.OK
		);
		
		Mockito.when(restemplate.exchange("http://localhost:8083/experian/12345",HttpMethod.GET,
						null, String.class)).thenReturn(creditScoreResult);
		Mockito.when(restemplate.exchange("http://localhost:8081/equifax/12345",HttpMethod.GET,
		null, String.class)).thenReturn(creditScoreResult);
		Mockito.when(restemplate.exchange("http://localhost:8084/transunion/12345",HttpMethod.GET,
		null, String.class)).thenReturn(creditScoreResult);

		UserDetails userInfo = creditService.getCreditScore(userDetails);
		
		assertEquals(userDetails.getFirstName(), userInfo.getFirstName());
		
	}

}
