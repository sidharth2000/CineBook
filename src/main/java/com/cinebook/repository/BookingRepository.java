package com.cinebook.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.Booking;
import com.cinebook.model.BookingStatus;
import com.cinebook.model.Seat;
import com.cinebook.model.ShowTime;

public interface BookingRepository extends JpaRepository<Booking,UUID> {
	
	List<Booking> findByShowTime_ShowTimeIdAndStatus_StatusNameIn(
	        Long showTimeId, List<String> statuses
	);

	List<Booking> findByShowTimeAndSeatsContainingAndStatusIn(ShowTime showTime, Seat seat, List<BookingStatus> of);
	
	Booking findByBookingId(UUID bookingId);	
}
