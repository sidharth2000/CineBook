/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * 
 * Concrete Command to lock seats
 * execute() locks the seats ie changes to PENDING 
 * undo() changes state to FAILURE (suppose user click backs without paying)
 */

package com.cinebook.command;

import java.time.LocalDateTime;
import java.util.List;

import com.cinebook.model.Booking;
import com.cinebook.model.BookingStatus;
import com.cinebook.model.Customer;
import com.cinebook.model.Seat;
import com.cinebook.model.ShowTime;
import com.cinebook.repository.BookingRepository;
import com.cinebook.repository.BookingStatusRepository;
import com.cinebook.repository.CustomerRepository;
import com.cinebook.repository.SeatRepository;
import com.cinebook.repository.ShowtimeRepository;

public class LockSeatsCommand implements BookingCommand {

	private final Long showTimeId;
	private final String customerEmail;
	private final List<Long> seatIds;

	private final BookingRepository bookingRepository;
	private final BookingStatusRepository bookingStatusRepository;
	private final CustomerRepository customerRepository;
	private final SeatRepository seatRepository;
	private final ShowtimeRepository showTimeRepository;
	private final float taxPercentage;

	private Booking booking;

	public LockSeatsCommand(Long showTimeId, String customerEmail, List<Long> seatIds,
			BookingRepository bookingRepository, BookingStatusRepository bookingStatusRepository,
			CustomerRepository customerRepository, SeatRepository seatRepository, ShowtimeRepository showTimeRepository,
			float taxPercentage, Booking booking) {
		this.showTimeId = showTimeId;
		this.customerEmail = customerEmail;
		this.seatIds = seatIds;
		this.bookingRepository = bookingRepository;
		this.customerRepository = customerRepository;
		this.seatRepository = seatRepository;
		this.showTimeRepository = showTimeRepository;
		this.taxPercentage = taxPercentage;
		this.booking = booking;
		this.bookingStatusRepository = bookingStatusRepository;
	}

	@Override
	public void execute() {
		Customer user = customerRepository.findByEmail(customerEmail);

		ShowTime showTime = showTimeRepository.findByShowTimeId(showTimeId);

		List<Seat> seats = seatRepository.findAllById(seatIds);

		BookingStatus pendingStatus = bookingStatusRepository.findByStatusName("PENDING");
		BookingStatus successStatus = bookingStatusRepository.findByStatusName("SUCCESS");

		boolean allAvailable = true;

		for (Seat seat : seats) {
			List<Booking> existingBookings = bookingRepository.findByShowTimeAndSeatsContainingAndStatusIn(showTime,
					seat, List.of(pendingStatus, successStatus));
			if (!existingBookings.isEmpty()) {
				allAvailable = false;
				break;
			}
		}

		if (!allAvailable) {
			throw new RuntimeException("One or more selected seats are already booked or locked");
		}

		float totalPrice = 0f;
		for (Seat s : seats) {
			totalPrice += (s.getSeatCategory().getPriceMultiplier() * showTime.getPrice());
		}

		if (booking == null) {
			booking = new Booking();
			booking.setCustomer(user);
			booking.setShowTime(showTime);
			booking.setSeats(seats);
			booking.setStatus(pendingStatus);
			booking.setTaxPercentage(taxPercentage);
			float taxAmount = totalPrice * taxPercentage / 100;
			booking.setTaxAmount(taxAmount);
			booking.setTotalPrice(totalPrice + taxAmount);
			booking.setCreatedAt(LocalDateTime.now());
			booking.setModifiedAt(LocalDateTime.now());
			booking.setDiscountedAmount(0.0f);
			booking.setLoyaltyPointsEarned(0);
			bookingRepository.save(booking);
		}
	}

	@Override
	public void undo() {
		if (booking != null) {
			BookingStatus failureStatus = bookingStatusRepository.findByStatusName("FAILURE");
			booking.setStatus(failureStatus);
			booking.setModifiedAt(LocalDateTime.now());
			bookingRepository.save(booking);
		}
	}

	@Override
	public Booking getBooking() {
		return booking;
	}
}
