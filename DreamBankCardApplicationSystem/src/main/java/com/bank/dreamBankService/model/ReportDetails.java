package com.bank.dreamBankService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ReportDetails")
public class ReportDetails {
	
		@Id 
		private int userID;
		private String Address;
		private String zipcode;
		private String decision;
		
		
		public String getAddress() {
			return Address;
		}
		public void setAddress(String address) {
			Address = address;
		}
		public String getZipcode() {
			return zipcode;
		}
		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}
		public String getDecision() {
			return decision;
		}
		public void setDecision(String decision) {
			this.decision = decision;
		}
		public int getUserID() {
			return userID;
		}
		public void setUserID(int userID) {
			this.userID = userID;
		}
	}
