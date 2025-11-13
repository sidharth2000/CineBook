package com.cinebook.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer extends User {

	@Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @Column(name = "registered_date")
    private LocalDateTime registeredDate;

    @Column(name = "modified_date")
    private LocalDateTime modifiedDate;
    
    public Customer() {
        this.registeredDate = LocalDateTime.now();
        this.loyaltyPoints = 0;
    }

	public Integer getLoyaltyPoints() {
		return loyaltyPoints;
	}

	public void setLoyaltyPoints(Integer loyaltyPoints) {
		this.loyaltyPoints = loyaltyPoints;
	}

	public LocalDateTime getRegisteredDate() {
		return registeredDate;
	}

	public void setRegisteredDate(LocalDateTime registeredDate) {
		this.registeredDate = registeredDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
    
    
}
