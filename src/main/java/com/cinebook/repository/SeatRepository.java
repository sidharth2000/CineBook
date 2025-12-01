package com.cinebook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.Screen;
import com.cinebook.model.Seat;

public interface SeatRepository extends JpaRepository<Seat,Long> {
	List<Seat> findByScreen(Screen screen);
}
