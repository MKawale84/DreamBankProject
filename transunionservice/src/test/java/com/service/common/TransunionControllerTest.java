package com.service.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.properties")
class TransunionControllerTest {
	@InjectMocks
	TransunionController controller;
	
	@Test
	void testGetScore() throws InterruptedException {
		Integer val = controller.getScore("12345");
		assertNotNull(val.intValue());
		val = controller.getScore(null);
		assertEquals(0,val.intValue());
	}

}
