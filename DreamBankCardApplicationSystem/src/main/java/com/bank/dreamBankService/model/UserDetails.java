package com.bank.dreamBankService.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "UserDetails")

public class UserDetails {

	@Id 
	private int id;
	private String FirstName;
	private String LastName;
	private String Address;
	private String PhoneNumber;
	private String zipcode;
	private String ssn;
	private String dob;
	private Integer TransunionScore;
	private Integer EquifaxScore;
	private Integer ExperianScore;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public Integer getTransunionScore() {
		return TransunionScore;
	}
	public void setTransunionScore(Integer transunionScore) {
		TransunionScore = transunionScore;
	}
	public Integer getEquifaxScore() {
		return EquifaxScore;
	}
	public void setEquifaxScore(Integer equifaxScore) {
		EquifaxScore = equifaxScore;
	}
	public Integer getExperianScore() {
		return ExperianScore;
	}
	public void setExperianScore(Integer experianScore) {
		ExperianScore = experianScore;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getFirstName() {
		return FirstName;
	}
	public void setFirstName(String firstName) {
		FirstName = firstName;
	}
	public String getLastName() {
		return LastName;
	}
	public void setLastName(String lastName) {
		LastName = lastName;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	
}
