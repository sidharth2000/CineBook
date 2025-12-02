package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.BookingStatus;

public interface BookingStatusRepository extends JpaRepository<BookingStatus, Integer> {
	
	BookingStatus findByStatusName(String statusName);
}