/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.dto;

import java.util.List;

public class TheatreOnboardingRequest {

	private String theatreName;
	private String overview;
	private String contactNumber;
	private AddressRequest address;
	private List<ScreenRequest> screens;

	public static class AddressRequest {

		private String addressLine1;
		private String addressLine2;
		private String county;
		private String eirCode;

		public String getAddressLine1() {
			return addressLine1;
		}

		public void setAddressLine1(String addressLine1) {
			this.addressLine1 = addressLine1;
		}

		public String getAddressLine2() {
			return addressLine2;
		}

		public void setAddressLine2(String addressLine2) {
			this.addressLine2 = addressLine2;
		}

		public String getCounty() {
			return county;
		}

		public void setCounty(String county) {
			this.county = county;
		}

		public String getEirCode() {
			return eirCode;
		}

		public void setEirCode(String eirCode) {
			this.eirCode = eirCode;
		}

	}

	public static class ScreenRequest {

		private String screenName;
		private List<Character> rowLabels;
		private int numColumns;
		private List<SeatCategoryRequest> seatCategories;

		public String getScreenName() {
			return screenName;
		}

		public void setScreenName(String screenName) {
			this.screenName = screenName;
		}

		public List<Character> getRowLabels() {
			return rowLabels;
		}

		public void setRowLabels(List<Character> rowLabels) {
			this.rowLabels = rowLabels;
		}

		public int getNumColumns() {
			return numColumns;
		}

		public void setNumColumns(int numColumns) {
			this.numColumns = numColumns;
		}

		public List<SeatCategoryRequest> getSeatCategories() {
			return seatCategories;
		}

		public void setSeatCategories(List<SeatCategoryRequest> seatCategories) {
			this.seatCategories = seatCategories;
		}

	}

	public static class SeatCategoryRequest {

		private String categoryName;
		private String description;
		private double priceMultiplier;
		private List<Character> rowsCovered;

		public String getCategoryName() {
			return categoryName;
		}

		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public double getPriceMultiplier() {
			return priceMultiplier;
		}

		public void setPriceMultiplier(double priceMultiplier) {
			this.priceMultiplier = priceMultiplier;
		}

		public List<Character> getRowsCovered() {
			return rowsCovered;
		}

		public void setRowsCovered(List<Character> rowsCovered) {
			this.rowsCovered = rowsCovered;
		}

	}

	public String getTheatreName() {
		return theatreName;
	}

	public void setTheatreName(String theatreName) {
		this.theatreName = theatreName;
	}

	public String getOverview() {
		return overview;
	}

	public void setOverview(String overview) {
		this.overview = overview;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public AddressRequest getAddress() {
		return address;
	}

	public void setAddress(AddressRequest address) {
		this.address = address;
	}

	public List<ScreenRequest> getScreens() {
		return screens;
	}

	public void setScreens(List<ScreenRequest> screens) {
		this.screens = screens;
	}

}
