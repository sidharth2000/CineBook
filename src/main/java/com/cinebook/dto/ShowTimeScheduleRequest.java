package com.cinebook.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class ShowTimeScheduleRequest {
    private Long movieId;
    private Integer formatId;
    private Integer languageId;
    private Integer subtitleLanguageId;
    private Long screenId;

    private LocalDate startDate;
    private LocalDate endDate;

    private LocalTime startTime;
    private Float basePrice;

    // Getters and Setters
    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    
    
    public Integer getFormatId() {
		return formatId;
	}
	public void setFormatId(Integer formatId) {
		this.formatId = formatId;
	}
	public Integer getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	public Integer getSubtitleLanguageId() {
		return subtitleLanguageId;
	}
	public void setSubtitleLanguageId(Integer subtitleLanguageId) {
		this.subtitleLanguageId = subtitleLanguageId;
	}
	public Long getScreenId() { return screenId; }
    public void setScreenId(Long screenId) { this.screenId = screenId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }


    public Float getBasePrice() { return basePrice; }
    public void setBasePrice(Float basePrice) { this.basePrice = basePrice; }
}
