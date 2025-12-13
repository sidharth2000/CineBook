/**
 * @author Abhaydev and Mathew
 * 
 * 
 */

package com.cinebook.dto;

import java.time.LocalDate;
import java.util.List;

public class MovieResponse {
	private Long movieId;
	private String movieTitle;
	private LocalDate releaseDate;
	private String posterUrl;

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public void setPosterUrl(String posterUrl) {
		this.posterUrl = posterUrl;
	}

}
