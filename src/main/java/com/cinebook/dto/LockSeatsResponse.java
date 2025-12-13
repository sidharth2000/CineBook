/**
 * @author Sidharthan jayavelu
 * 
 */

package com.cinebook.dto;

import java.util.List;

public class LockSeatsResponse {
	private String bookingId;
	private List<String> lockedSeats;
	private Float totalPrice;
	private Float taxAmount;
	private Float taxPercentage;

	// Getters and Setters
	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public List<String> getLockedSeats() {
		return lockedSeats;
	}

	public void setLockedSeats(List<String> lockedSeats) {
		this.lockedSeats = lockedSeats;
	}

	public Float getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Float totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Float getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Float taxAmount) {
		this.taxAmount = taxAmount;
	}

	public Float getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Float taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

}
