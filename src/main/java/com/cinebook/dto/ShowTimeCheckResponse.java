/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.dto;

import java.time.LocalTime;

public class ShowTimeCheckResponse {
	private boolean valid;
	private LocalTime calculatedEndTime; // null if invalid
	private String message;

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public LocalTime getCalculatedEndTime() {
		return calculatedEndTime;
	}

	public void setCalculatedEndTime(LocalTime calculatedEndTime) {
		this.calculatedEndTime = calculatedEndTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
