package com.cinebook.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seatId;

	private String seatName;
	private String rowName;
	private int columnNumber;

	@ManyToOne
	@JoinColumn(name = "seat_category_id")
	private SeatCategory seatCategory;
	
	@ManyToOne
	@JoinColumn(name= "screen_id")
	private Screen screen;

	public Long getSeatId() {
		return seatId;
	}

	public void setSeatId(Long seatId) {
		this.seatId = seatId;
	}

	public String getSeatName() {
		return seatName;
	}

	public void setSeatName(String seatName) {
		this.seatName = seatName;
	}

	public String getRowName() {
		return rowName;
	}

	public void setRowName(String rowName) {
		this.rowName = rowName;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public void setColumnNumber(int columnNumber) {
		this.columnNumber = columnNumber;
	}

	public SeatCategory getSeatCategory() {
		return seatCategory;
	}

	public void setSeatCategory(SeatCategory seatCategory) {
		this.seatCategory = seatCategory;
	}

	public Screen getScreen() {
		return screen;
	}

	public void setScreen(Screen screen) {
		this.screen = screen;
	}
	
	
	
}
