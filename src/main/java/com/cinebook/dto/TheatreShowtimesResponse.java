/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import com.cinebook.model.Address;

public class TheatreShowtimesResponse {
	private Long theatreId;
	private String theatreName;
	private String theatreOverview;
	private Address theatreAddress;
	private Map<LocalDate, List<MovieShowtimeGroup>> schedule;

	public static class MovieShowtimeGroup {
		private Long movieId;
		private String movieTitle;
		private String posterUrl;
		private List<ShowTimeSlot> showtimes;

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

		public String getPosterUrl() {
			return posterUrl;
		}

		public void setPosterUrl(String posterUrl) {
			this.posterUrl = posterUrl;
		}

		public List<ShowTimeSlot> getShowtimes() {
			return showtimes;
		}

		public void setShowtimes(List<ShowTimeSlot> showtimes) {
			this.showtimes = showtimes;
		}

	}

	public static class ShowTimeSlot {
		private Long showTimeId;
		private LocalTime startTime;
		private LocalTime endTime;
		private String format;
		private String language;
		private String subtitleLanguage;
		private Float price;

		public Long getShowTimeId() {
			return showTimeId;
		}

		public void setShowTimeId(Long showTimeId) {
			this.showTimeId = showTimeId;
		}

		public LocalTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalTime startTime) {
			this.startTime = startTime;
		}

		public LocalTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalTime endTime) {
			this.endTime = endTime;
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public String getSubtitleLanguage() {
			return subtitleLanguage;
		}

		public void setSubtitleLanguage(String subtitleLanguage) {
			this.subtitleLanguage = subtitleLanguage;
		}

		public Float getPrice() {
			return price;
		}

		public void setPrice(Float price) {
			this.price = price;
		}

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

	public Map<LocalDate, List<MovieShowtimeGroup>> getSchedule() {
		return schedule;
	}

	public void setSchedule(Map<LocalDate, List<MovieShowtimeGroup>> schedule) {
		this.schedule = schedule;
	}

	public String getTheatreOverview() {
		return theatreOverview;
	}

	public void setTheatreOverview(String theatreOverview) {
		this.theatreOverview = theatreOverview;
	}

	public Address getTheatreAddress() {
		return theatreAddress;
	}

	public void setTheatreAddress(Address theatreAddress) {
		this.theatreAddress = theatreAddress;
	}

}
