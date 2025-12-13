/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.dto;

public class TheatreBrowseResponse {
	private Long theatreId;
	private String theatreName;
	private String overview;
	private String county;

	public TheatreBrowseResponse(Long theatreId, String theatreName, String overview, String county) {
		this.theatreId = theatreId;
		this.theatreName = theatreName;
		this.overview = overview;
		this.county = county;
	}

	public Long getTheatreId() {
		return theatreId;
	}

	public void setTheatreId(Long theatreId) {
		this.theatreId = theatreId;
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

	public String getCounty() {
		return county;
	}

	public void setCounty(String county) {
		this.county = county;
	}
}
