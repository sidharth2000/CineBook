package com.cinebook.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class PromoCode extends DiscountCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer promoCodeId;

    @Column(nullable = false)
    private LocalDateTime startDate;            

    @Column(nullable = false)
    private LocalDateTime expiryDate;

    @Column(nullable = false)
    private Integer maxUsePerCustomer;

	public Integer getPromoCodeId() {
		return promoCodeId;
	}

	public void setPromoCodeId(Integer promoCodeId) {
		this.promoCodeId = promoCodeId;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getMaxUsePerCustomer() {
		return maxUsePerCustomer;
	}

	public void setMaxUsePerCustomer(Integer maxUsePerCustomer) {
		this.maxUsePerCustomer = maxUsePerCustomer;
	}

    
}