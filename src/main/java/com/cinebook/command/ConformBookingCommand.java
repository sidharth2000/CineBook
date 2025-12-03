package com.cinebook.command;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cinebook.model.Booking;
import com.cinebook.model.BookingStatus;
import com.cinebook.repository.BookingRepository;
import com.cinebook.repository.BookingStatusRepository;

public class ConformBookingCommand implements BookingCommand {

    private final UUID bookingId;
    private final BookingRepository bookingRepository;
    private final BookingStatusRepository bookingStatusRepository;
    private final int lockDurationMinutes = 5;
    private final float loyaltyFactor;

    private Booking booking;
    

    public ConformBookingCommand(UUID bookingId,
                                 BookingRepository bookingRepository,
                                 BookingStatusRepository bookingStatusRepository,
                                 float loyaltyFactor) {
        this.bookingId = bookingId;
        this.bookingRepository = bookingRepository;
        this.bookingStatusRepository = bookingStatusRepository;
        this.loyaltyFactor = loyaltyFactor;
    }

    @Override
    public void execute() {
        booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lockExpiry = booking.getCreatedAt().plusMinutes(lockDurationMinutes);

        if (now.isAfter(lockExpiry)) {
            // Session expired → mark FAILURE and remove discount
            BookingStatus failureStatus = bookingStatusRepository.findByStatusName("FAILURE");
            booking.setStatus(failureStatus);
            removeDiscounts();
            booking.setModifiedAt(now);
            bookingRepository.save(booking);
            throw new RuntimeException("Booking session expired. Cannot confirm booking.");
        }

        // Session valid → mark SUCCESS
        BookingStatus successStatus = bookingStatusRepository.findByStatusName("SUCCESS");
        booking.setStatus(successStatus);
        booking.setModifiedAt(now);
        
        int loyaltyPoints = Math.round(booking.getTotalPrice() * loyaltyFactor);
        booking.setLoyaltyPointsEarned(loyaltyPoints);
        
        bookingRepository.save(booking);
    }

    @Override
    public void undo() {
        booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found for undo"));

        // Cancel the booking → mark CANCELLED and remove discount
        BookingStatus cancelledStatus = bookingStatusRepository.findByStatusName("CANCELLED");
        booking.setStatus(cancelledStatus);
        removeDiscounts();
        booking.setModifiedAt(LocalDateTime.now());
        bookingRepository.save(booking);
    }

    @Override
    public Booking getBooking() {
        return booking;
    }

    // Utility method to remove applied discounts
    private void removeDiscounts() {
        booking.setDiscountedAmount(0f);
        booking.setPromocodeUsed(null);
        booking.setVoucherApplied(null);
    }
}
