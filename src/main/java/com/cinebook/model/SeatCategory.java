package com.cinebook.model;

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
public class SeatCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatCategoryId;

    private String categoryName;
    private String description;
    private Double priceMultiplier;

    @OneToMany(mappedBy = "seatCategory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

	public Long getSeatCategoryId() {
		return seatCategoryId;
	}

	public void setSeatCategoryId(Long seatCategoryId) {
		this.seatCategoryId = seatCategoryId;
	}

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

	
	public List<Seat> getSeats() {
		return seats;
	}

	public void setSeats(List<Seat> seats) {
		this.seats = seats;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}

	public Double getPriceMultiplier() {
		return priceMultiplier;
	}

	public void setPriceMultiplier(Double priceMultiplier) {
		this.priceMultiplier = priceMultiplier;
	}
	
	
    
    
}
