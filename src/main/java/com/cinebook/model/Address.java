package com.cinebook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "address")
public class Address {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long addressId;

    private String addressLine1;
    private String addressLine2;
    private String county;
    private String eirCode;
   
    
	public Address() {
	
	}
	
	
	public Long getAddressId() {
		return addressId;
	}
	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getEirCode() {
		return eirCode;
	}
	public void setEirCode(String eirCode) {
		this.eirCode = eirCode;
	}
    
    

}
