package com.cinebook.dto;

public class ApplyDiscountResponse {
	private String bookingId;
    private Float originalPrice;
    private Float discountedAmount;
    private Float finalPrice;
	public String getBookingId() {
		return bookingId;
	}
	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}
	public Float getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(Float originalPrice) {
		this.originalPrice = originalPrice;
	}
	public Float getDiscountedAmount() {
		return discountedAmount;
	}
	public void setDiscountedAmount(Float discountedAmount) {
		this.discountedAmount = discountedAmount;
	}
	public Float getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(Float finalPrice) {
		this.finalPrice = finalPrice;
	}

    
    
}
