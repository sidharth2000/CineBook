package com.cinebook.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.Booking;
import com.cinebook.model.BookingStatus;
import com.cinebook.model.Customer;
import com.cinebook.model.PromoCode;
import com.cinebook.model.Seat;
import com.cinebook.model.ShowTime;
import com.cinebook.model.VoucherCode;

public interface BookingRepository extends JpaRepository<Booking,UUID> {
	
	List<Booking> findByShowTime_ShowTimeIdAndStatus_StatusNameIn(
	        Long showTimeId, List<String> statuses
	);

	List<Booking> findByShowTimeAndSeatsContainingAndStatusIn(ShowTime showTime, Seat seat, List<BookingStatus> of);
	
	Booking findByBookingId(UUID bookingId);

	long countByVoucherApplied(VoucherCode voucher);

	long countByCustomerAndPromocodeUsed(Customer customer, PromoCode promo);	
}
