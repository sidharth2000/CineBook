package com.cinebook.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Screen {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long screenId;

    private String screenName;
    private int totalSeats;

    @OneToMany(mappedBy = "screen", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatCategory> seatCategories;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    
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
	public int getTotalSeats() {
		return totalSeats;
	}
	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}
	public List<SeatCategory> getSeatCategories() {
		return seatCategories;
	}
	public void setSeatCategories(List<SeatCategory> seatCategories) {
		this.seatCategories = seatCategories;
	}
	public Theatre getTheatre() {
		return theatre;
	}
	public void setTheatre(Theatre theatre) {
		this.theatre = theatre;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
}
