/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.service;

import java.util.List;
import java.util.UUID;

import com.cinebook.dto.ApplyDiscountResponse;
import com.cinebook.dto.BrowseTheatreRequest;
import com.cinebook.dto.ConfirmBookingResponse;
import com.cinebook.dto.LockSeatsResponse;
import com.cinebook.dto.MovieBrowseResponse;
import com.cinebook.dto.ShowTimeDetailsResponse;
import com.cinebook.dto.TheatreBrowseResponse;
import com.cinebook.dto.TheatreShowtimesResponse;

public interface BookingService {

	List<TheatreBrowseResponse> browseTheatres(BrowseTheatreRequest request);

	List<MovieBrowseResponse> getAvailableMovies(List<Long> languageIds, List<Long> formatIds, String genre,
			String search, boolean sortAsc);

	TheatreShowtimesResponse getShowtimesByTheatre(Long theatreId);

	ShowTimeDetailsResponse getShowTimeDetails(Long showTimeId);

	LockSeatsResponse lockSeats(Long showTimeId, String customerEmail, List<Long> seatIds);

	ApplyDiscountResponse applyDiscount(UUID bookingId, String discountCode, String customerEmail);

	ApplyDiscountResponse undoDiscount(UUID bookingId, String customerEmail);

	ConfirmBookingResponse confirmBooking(UUID bookingId);

	ConfirmBookingResponse cancelBooking(UUID bookingId);
}
