package com.bank.dreamBankService;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import com.bank.dreamBankService.model.ReportDetails;

class ReportDetailsTest {

	 @Test
	    public void testSetter_setsProperly() throws NoSuchFieldException, IllegalAccessException {
	        //given
	        final ReportDetails pojo = new ReportDetails();

	        //when
	        pojo.setAddress("Phoenix");
	        pojo.setDecision("Approved");
	        pojo.setUserID(1212);
	        pojo.setZipcode("1234");

	        //then
	        Field field = pojo.getClass().getDeclaredField("Address");
	        field.setAccessible(true);
	        assertEquals(field.get(pojo), "Phoenix");
	        field = pojo.getClass().getDeclaredField("decision");
	        field.setAccessible(true);
	        assertEquals(field.get(pojo), "Approved");
	        field = pojo.getClass().getDeclaredField("userID");
	        field.setAccessible(true);
	        assertEquals(field.get(pojo), 1212);
	        field = pojo.getClass().getDeclaredField("zipcode");
	        field.setAccessible(true);
	        assertEquals(field.get(pojo), "1234");
	    }

	    @Test
	    public void testGetter_getsValue() throws NoSuchFieldException, IllegalAccessException {
	        //given
	    	final ReportDetails pojo = new ReportDetails();
	        Field field = pojo.getClass().getDeclaredField("Address");
	        field.setAccessible(true);
	        field.set(pojo, "Phoenix");
	        assertEquals(pojo.getAddress(), "Phoenix");
	        field = pojo.getClass().getDeclaredField("decision");
	        field.setAccessible(true);
	        field.set(pojo, "Approved");
	        assertEquals(pojo.getDecision(), "Approved");
	        field = pojo.getClass().getDeclaredField("zipcode");
	        field.setAccessible(true);
	        field.set(pojo, "1234");
	        assertEquals(pojo.getZipcode(), "1234");
	        field = pojo.getClass().getDeclaredField("userID");
	        field.setAccessible(true);
	        field.set(pojo, 1212);
	        assertEquals(pojo.getUserID(), 1212);
	    }
}
