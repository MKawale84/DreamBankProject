package com.bank.dreamBankService;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Test;

import com.bank.dreamBankService.model.UserDetails;

class UserDetailsTest {
	
	@Test
    public void testSetter_setsProperly() throws NoSuchFieldException, IllegalAccessException {
        //given
        final UserDetails pojo = new UserDetails();

        //when
        pojo.setFirstName("Test1");
        pojo.setLastName("Test2");
        pojo.setAddress("Phoenix");
        pojo.setPhoneNumber("1231231234");
        pojo.setSsn("111");
        pojo.setZipcode("85050");
        pojo.setEquifaxScore(720);
        pojo.setExperianScore(820);
        pojo.setTransunionScore(800);
       

        //then
        Field field = pojo.getClass().getDeclaredField("FirstName");
        field.setAccessible(true);
        assertEquals(field.get(pojo), "Test1");
        field = pojo.getClass().getDeclaredField("LastName");
        field.setAccessible(true);
        assertEquals(field.get(pojo), "Test2");
        field = pojo.getClass().getDeclaredField("Address");
        field.setAccessible(true);
        assertEquals(field.get(pojo), "Phoenix");
        field = pojo.getClass().getDeclaredField("PhoneNumber");
        field.setAccessible(true);
        assertEquals(field.get(pojo), "1231231234");
        field = pojo.getClass().getDeclaredField("ssn");
        field.setAccessible(true);
        assertEquals(field.get(pojo), "111");
        field = pojo.getClass().getDeclaredField("TransunionScore");
        field.setAccessible(true);
        assertEquals(field.get(pojo), 800);
        field = pojo.getClass().getDeclaredField("EquifaxScore");
        field.setAccessible(true);
        assertEquals(field.get(pojo), 720);
        field = pojo.getClass().getDeclaredField("ExperianScore");
        field.setAccessible(true);
        assertEquals(field.get(pojo), 820);
    }

    @Test
    public void testGetter_getsValue() throws NoSuchFieldException, IllegalAccessException {
        //given
    	final UserDetails pojo = new UserDetails();
        Field field = pojo.getClass().getDeclaredField("FirstName");
        field.setAccessible(true);
        field.set(pojo, "Test1");
        assertEquals(pojo.getFirstName(), "Test1");
        field = pojo.getClass().getDeclaredField("LastName");
        field.setAccessible(true);
        field.set(pojo, "Test2");
        assertEquals(pojo.getLastName(), "Test2");
        field = pojo.getClass().getDeclaredField("Address");
        field.setAccessible(true);
        field.set(pojo, "Phoenix");
        assertEquals(pojo.getAddress(), "Phoenix");
        field = pojo.getClass().getDeclaredField("PhoneNumber");
        field.setAccessible(true);
        field.set(pojo, "1231231234");
        assertEquals(pojo.getPhoneNumber(), "1231231234");
        field = pojo.getClass().getDeclaredField("ssn");
        field.setAccessible(true);
        field.set(pojo, "111");
        assertEquals(pojo.getSsn(), "111");
        field = pojo.getClass().getDeclaredField("TransunionScore");
        field.setAccessible(true);
        field.set(pojo, 800);
        assertEquals(pojo.getTransunionScore(), 800);
        field = pojo.getClass().getDeclaredField("EquifaxScore");
        field.setAccessible(true);
        field.set(pojo, 800);
        assertEquals(pojo.getTransunionScore(), 800);
        field = pojo.getClass().getDeclaredField("ExperianScore");
        field.setAccessible(true);
        field.set(pojo, 800);
        assertEquals(pojo.getTransunionScore(), 800);
    }

}
