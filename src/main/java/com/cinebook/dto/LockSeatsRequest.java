/**
 * @author Sidharthan jayavelu
 * 
 */

package com.cinebook.dto;

import java.util.List;

public class LockSeatsRequest {
	private Long showTimeId;
	private List<Long> seatIds;

	// Getters and Setters
	public Long getShowTimeId() {
		return showTimeId;
	}

	public void setShowTimeId(Long showTimeId) {
		this.showTimeId = showTimeId;
	}

	public List<Long> getSeatIds() {
		return seatIds;
	}

	public void setSeatIds(List<Long> seatIds) {
		this.seatIds = seatIds;
	}
}
