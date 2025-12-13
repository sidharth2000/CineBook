/**
 * @author Sidharthan jayavelu
 * 
 */

package com.cinebook.dto;

import java.util.UUID;

public class ApplyDiscountRequest {
	private UUID bookingId;
	private String discountCode;

	// Getters & Setters
	public UUID getBookingId() {
		return bookingId;
	}

	public void setBookingId(UUID bookingId) {
		this.bookingId = bookingId;
	}

	public String getDiscountCode() {
		return discountCode;
	}

	public void setDiscountCode(String discountCode) {
		this.discountCode = discountCode;
	}
}
