package com.cinebook.dto;

import java.util.UUID;

// Request for cancelling booking
public class CancelBookingRequest {
    private UUID bookingId;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }
}
