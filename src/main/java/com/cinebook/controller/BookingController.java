/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * Booking controller handles the browse by theatre , view showtimes , select to view seat information
 * and lock -> apply discount/cancel discount -> confirm -> cancel bookings
 * 
 */

package com.cinebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.ApplyDiscountRequest;
import com.cinebook.dto.ApplyDiscountResponse;
import com.cinebook.dto.BrowseTheatreRequest;
import com.cinebook.dto.CancelBookingRequest;
import com.cinebook.dto.CancelDiscountRequest;
import com.cinebook.dto.ConfirmBookingRequest;
import com.cinebook.dto.ConfirmBookingResponse;
import com.cinebook.dto.LockSeatsRequest;
import com.cinebook.dto.LockSeatsResponse;
import com.cinebook.dto.ShowTimeDetailsResponse;
import com.cinebook.dto.TheatreBrowseResponse;
import com.cinebook.dto.TheatreShowtimesResponse;
import com.cinebook.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;

	// API to browse by Theatre
	@PostMapping("/browse/theatres")
	public ResponseEntity<ApiResponse<List<TheatreBrowseResponse>>> browseTheatres(
			@RequestBody BrowseTheatreRequest request) {

		List<TheatreBrowseResponse> theatres = bookingService.browseTheatres(request);

		ApiResponse<List<TheatreBrowseResponse>> response = new ApiResponse<>("success", theatres,
				"Theatres retrieved successfully");

		return ResponseEntity.ok(response);

	}

	// API to browse all showtime in selcted Theatre by dates
	@GetMapping("/browse/theatres/{theatreId}/showtimes")
	public ResponseEntity<ApiResponse<TheatreShowtimesResponse>> getShowtimesByTheatre(@PathVariable Long theatreId) {

		TheatreShowtimesResponse theatreShowtimes = bookingService.getShowtimesByTheatre(theatreId);

		ApiResponse<TheatreShowtimesResponse> response = new ApiResponse<>("success", theatreShowtimes,
				"Showtimes retrieved successfully");

		return ResponseEntity.ok(response);

	}

	// API to get showtime details - seat layout and statuses - once cliked the showtime prev by id
	@GetMapping("/showtime/{showTimeId}/details")
	public ResponseEntity<ApiResponse<ShowTimeDetailsResponse>> getShowTimeDetails(@PathVariable Long showTimeId) {

		ShowTimeDetailsResponse data = bookingService.getShowTimeDetails(showTimeId);

		return ResponseEntity.ok(new ApiResponse<>("success", data, "Showtime details retrieved"));

	}

	// API to apply lock and return price and details -- after slecting seats and clicking on next
	@PostMapping("/lock-seats")
	public ResponseEntity<ApiResponse<LockSeatsResponse>> lockSeats(@RequestBody LockSeatsRequest request) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String customerEmail = userDetails.getUsername();

		LockSeatsResponse response = bookingService.lockSeats(request.getShowTimeId(), customerEmail,
				request.getSeatIds());

		return ResponseEntity.ok(new ApiResponse<>("success", response, "Seats locked successfully"));

	}

	// API to apply discount -- after entering promo code and get latest price
	@PostMapping("/apply-discount")
	public ResponseEntity<ApiResponse<ApplyDiscountResponse>> applyDiscount(@RequestBody ApplyDiscountRequest request) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ApplyDiscountResponse response = bookingService.applyDiscount(request.getBookingId(), request.getDiscountCode(),
				userDetails.getUsername());

		return ResponseEntity.ok(new ApiResponse<>("success", response, "Discount applied successfully"));

	}

	// API to remove entered discouint -- when user click x in the discount box
	@PostMapping("/cancel-discount")
	public ResponseEntity<ApiResponse<ApplyDiscountResponse>> cancelDiscount(
			@RequestBody CancelDiscountRequest request) {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		ApplyDiscountResponse response = bookingService.undoDiscount(request.getBookingId(), userDetails.getUsername());

		return ResponseEntity.ok(new ApiResponse<>("success", response, "Discount removed successfully"));

	}

	// API to conform the booking
	@PostMapping("/confirm-booking")
	public ResponseEntity<ApiResponse<ConfirmBookingResponse>> confirmBooking(
			@RequestBody ConfirmBookingRequest request) {
		ConfirmBookingResponse response = bookingService.confirmBooking(request.getBookingId());
		return ResponseEntity.ok(new ApiResponse<>("success", response, "Booking confirmed successfully"));

	}

	// API to cancel the booking later
	@PostMapping("/cancel-booking")
	public ResponseEntity<ApiResponse<ConfirmBookingResponse>> cancelBooking(
			@RequestBody CancelBookingRequest request) {

		ConfirmBookingResponse response = bookingService.cancelBooking(request.getBookingId());

		return ResponseEntity.ok(new ApiResponse<>("success", response, "Booking cancelled successfully"));

	}

}
