package com.cinebook.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class DiscountCode {

    @Column(nullable = false, unique = true)
    protected String code;

    @Column(nullable = false)
    protected Float discountPercentage;

    @Column(nullable = false)
    protected Float maxDiscountAmount;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Float getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(Float discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public Float getMaxDiscountAmount() {
		return maxDiscountAmount;
	}

	public void setMaxDiscountAmount(Float maxDiscountAmount) {
		this.maxDiscountAmount = maxDiscountAmount;
	}

    
}
