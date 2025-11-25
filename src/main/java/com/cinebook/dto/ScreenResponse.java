package com.cinebook.dto;

import java.util.List;

public class ScreenResponse {
	private Long screenId;
	private String screenName;
	private Integer totalSeats;
	private List<SeatCategoryResponse> seatCategories;

	public Long getScreenId() {
		return screenId;
	}

	public void setScreenId(Long screenId) {
		this.screenId = screenId;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Integer getTotalSeats() {
		return totalSeats;
	}

	public void setTotalSeats(Integer totalSeats) {
		this.totalSeats = totalSeats;
	}

	public List<SeatCategoryResponse> getSeatCategories() {
		return seatCategories;
	}

	public void setSeatCategories(List<SeatCategoryResponse> seatCategories) {
		this.seatCategories = seatCategories;
	}

}
