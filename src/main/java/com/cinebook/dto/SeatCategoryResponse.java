/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.dto;

public class SeatCategoryResponse {
	private Long categoryId;
	private String categoryName;
	private Double priceMultiplier;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Double getPriceMultiplier() {
		return priceMultiplier;
	}

	public void setPriceMultiplier(Double priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
	}

}