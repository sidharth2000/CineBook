package com.cinebook.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class VoucherType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer voucherTypeId;

    @Column(nullable = false, unique = true)
    private String voucherName;  // e.g., LOYAL100

    @Column(nullable = false)
    private Integer loyaltyPointsCost;

    @Column(nullable = false)
    private Float discountPercentage;

    @Column(nullable = false)
    private Float maxDiscountAmount;

    @Column(nullable = false)
    private Integer validityPeriodDays;

    @Column(nullable = false)
    private Boolean isActive;

	public Integer getVoucherTypeId() {
		return voucherTypeId;
	}

	public void setVoucherTypeId(Integer voucherTypeId) {
		this.voucherTypeId = voucherTypeId;
	}

	public String getVoucherName() {
		return voucherName;
	}

	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}

	public Integer getLoyaltyPointsCost() {
		return loyaltyPointsCost;
	}

	public void setLoyaltyPointsCost(Integer loyaltyPointsCost) {
		this.loyaltyPointsCost = loyaltyPointsCost;
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

	public Integer getValidityPeriodDays() {
		return validityPeriodDays;
	}

	public void setValidityPeriodDays(Integer validityPeriodDays) {
		this.validityPeriodDays = validityPeriodDays;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

    // getters & setters
}
