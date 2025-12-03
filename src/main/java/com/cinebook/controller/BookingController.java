package com.cinebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.cinebook.dto.BrowseMovieRequest;
import com.cinebook.dto.BrowseTheatreRequest;
import com.cinebook.dto.CancelBookingRequest;
import com.cinebook.dto.CancelDiscountRequest;
import com.cinebook.dto.ConfirmBookingRequest;
import com.cinebook.dto.ConfirmBookingResponse;
import com.cinebook.dto.LockSeatsRequest;
import com.cinebook.dto.LockSeatsResponse;
import com.cinebook.dto.MovieBrowseResponse;
import com.cinebook.dto.ShowTimeDetailsResponse;
import com.cinebook.dto.TheatreBrowseResponse;
import com.cinebook.dto.TheatreShowtimesResponse;
import com.cinebook.service.BookingService;

@RestController
@RequestMapping("/booking")
public class BookingController {

	@Autowired
	BookingService bookingService;

	@PostMapping("/browse/theatres")
	public ResponseEntity<ApiResponse<List<TheatreBrowseResponse>>> browseTheatres(
			@RequestBody BrowseTheatreRequest request) {
		try {
			// --- Role check ---
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			boolean isCustomer = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "CUSTOMER".equalsIgnoreCase(auth.getAuthority()));

			if (!isCustomer) {
				ApiResponse<List<TheatreBrowseResponse>> error = new ApiResponse<>("failure", null,
						"Access denied: Customers only");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
			}

			// --- Fetch theatres using service ---
			List<TheatreBrowseResponse> theatres = bookingService.browseTheatres(request);

			ApiResponse<List<TheatreBrowseResponse>> response = new ApiResponse<>("success", theatres,
					"Theatres retrieved successfully");

			return ResponseEntity.ok(response);

		} catch (Exception e) {
			ApiResponse<List<TheatreBrowseResponse>> error = new ApiResponse<>("failure", null, e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}

	@GetMapping("/browse/theatres/{theatreId}/showtimes")
	public ResponseEntity<ApiResponse<TheatreShowtimesResponse>> getShowtimesByTheatre(@PathVariable Long theatreId) {
		try {
			// --- Role check ---
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			boolean isCustomer = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "CUSTOMER".equalsIgnoreCase(auth.getAuthority()));

			if (!isCustomer) {
				ApiResponse<TheatreShowtimesResponse> error = new ApiResponse<>("failure", null,
						"Access denied: Customers only");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
			}

			// --- Fetch showtimes using service ---
			TheatreShowtimesResponse theatreShowtimes = bookingService.getShowtimesByTheatre(theatreId);

			ApiResponse<TheatreShowtimesResponse> response = new ApiResponse<>("success", theatreShowtimes,
					"Showtimes retrieved successfully");

			return ResponseEntity.ok(response);

		} catch (Exception e) {

			ApiResponse<TheatreShowtimesResponse> error = new ApiResponse<>("failure", null, e.getMessage());

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
		}
	}

	@PostMapping("/browse/movies")
	public ResponseEntity<ApiResponse<List<MovieBrowseResponse>>> browseMovies(
			@RequestBody BrowseMovieRequest request) {
		try {
			// --- Role check ---
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			boolean isCustomer = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "CUSTOMER".equalsIgnoreCase(auth.getAuthority()));

			if (!isCustomer) {
				ApiResponse<List<MovieBrowseResponse>> error = new ApiResponse<>("failure", null,
						"Access denied: Customers only");
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
			}

			// --- Fetch movies ---
			List<MovieBrowseResponse> movies = bookingService.getAvailableMovies(request.getLanguageIds(),
					request.getFormatIds(), request.getGenre(), request.getSearch(), request.isSortAsc());

			ApiResponse<List<MovieBrowseResponse>> response = new ApiResponse<>("success", movies,
					"Movies retrieved successfully");
			return ResponseEntity.ok(response);

		} catch (Exception e) {
			ApiResponse<List<MovieBrowseResponse>> error = new ApiResponse<>("failure", null, e.getMessage());
			return ResponseEntity.internalServerError().body(error);
		}
	}

	@GetMapping("/showtime/{showTimeId}/details")
	public ResponseEntity<ApiResponse<ShowTimeDetailsResponse>> getShowTimeDetails(@PathVariable Long showTimeId) {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			boolean isCustomer = userDetails.getAuthorities().stream()
					.anyMatch(a -> "CUSTOMER".equalsIgnoreCase(a.getAuthority()));

			if (!isCustomer) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>("failure", null, "Access denied: Customers only"));
			}

			ShowTimeDetailsResponse data = bookingService.getShowTimeDetails(showTimeId);

			return ResponseEntity.ok(new ApiResponse<>("success", data, "Showtime details retrieved"));

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new ApiResponse<>("failure", null, e.getMessage()));
		}
	}

	@PostMapping("/lock-seats")
	public ResponseEntity<ApiResponse<LockSeatsResponse>> lockSeats(@RequestBody LockSeatsRequest request) {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			String customerEmail = userDetails.getUsername();

			LockSeatsResponse response = bookingService.lockSeats(request.getShowTimeId(), customerEmail,
					request.getSeatIds());

			return ResponseEntity.ok(new ApiResponse<>("success", response, "Seats locked successfully"));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse<>("failure", null, e.getMessage()));
		}
	}

	@PostMapping("/apply-discount")
	public ResponseEntity<ApiResponse<ApplyDiscountResponse>> applyDiscount(@RequestBody ApplyDiscountRequest request) {
		try {
			// Get authenticated user
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			// Check if user has CUSTOMER role
			boolean isCustomer = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "CUSTOMER".equalsIgnoreCase(auth.getAuthority()));

			if (!isCustomer) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>("failure", null, "Access denied: Customers only"));
			}

			// Pass the bookingId, discountCode, and user email to service
			ApplyDiscountResponse response = bookingService.applyDiscount(request.getBookingId(),
					request.getDiscountCode(), userDetails.getUsername());

			return ResponseEntity.ok(new ApiResponse<>("success", response, "Discount applied successfully"));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse<>("failure", null, e.getMessage()));
		}
	}

	@PostMapping("/cancel-discount")
	public ResponseEntity<ApiResponse<ApplyDiscountResponse>> cancelDiscount(
			@RequestBody CancelDiscountRequest request) {
		try {
			// Get authenticated user
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();

			// Check if user has CUSTOMER role
			boolean isCustomer = userDetails.getAuthorities().stream()
					.anyMatch(auth -> "CUSTOMER".equalsIgnoreCase(auth.getAuthority()));

			if (!isCustomer) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body(new ApiResponse<>("failure", null, "Access denied: Customers only"));
			}

			// Pass the bookingId and user email to service to undo discount
			ApplyDiscountResponse response = bookingService.undoDiscount(request.getBookingId(),
					userDetails.getUsername());

			return ResponseEntity.ok(new ApiResponse<>("success", response, "Discount removed successfully"));

		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new ApiResponse<>("failure", null, e.getMessage()));
		}
	}
	
	
	@PostMapping("/confirm-booking")
	public ResponseEntity<ApiResponse<ConfirmBookingResponse>> confirmBooking(
	        @RequestBody ConfirmBookingRequest request) {
	    try {
	        // Get authenticated user
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
	                .getPrincipal();

	        // Check if user has CUSTOMER role
	        boolean isCustomer = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "CUSTOMER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isCustomer) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Customers only"));
	        }

	        // Call service to confirm booking
	        ConfirmBookingResponse response = bookingService.confirmBooking(request.getBookingId());

	        return ResponseEntity.ok(new ApiResponse<>("success", response, "Booking confirmed successfully"));

	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(new ApiResponse<>("failure", null, e.getMessage()));
	    }
	}
	
	
	@PostMapping("/cancel-booking")
	public ResponseEntity<ApiResponse<ConfirmBookingResponse>> cancelBooking(
	        @RequestBody CancelBookingRequest request) {
	    try {
	        // Get authenticated user
	        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
	                .getPrincipal();

	        // Check if user has CUSTOMER role
	        boolean isCustomer = userDetails.getAuthorities().stream()
	                .anyMatch(auth -> "CUSTOMER".equalsIgnoreCase(auth.getAuthority()));

	        if (!isCustomer) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                    .body(new ApiResponse<>("failure", null, "Access denied: Customers only"));
	        }

	        // Call service to cancel booking
	        ConfirmBookingResponse response = bookingService.cancelBooking(request.getBookingId());

	        return ResponseEntity.ok(new ApiResponse<>("success", response, "Booking cancelled successfully"));

	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body(new ApiResponse<>("failure", null, e.getMessage()));
	    }
	}

}
