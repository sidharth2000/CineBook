/**
 * @author Sidharthan jayavelu
 * 
 */

package com.cinebook.dto;

public class ApiResponse<T> {
	private String status;
	private T payload;
	private String message;

	public ApiResponse() {
	}

	public ApiResponse(String status, T payload, String message) {
		this.status = status;
		this.payload = payload;
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public T getPayload() {
		return payload;
	}

	public void setPayload(T payload) {
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
