package com.cinebook.dto;

import java.util.UUID;

// Request for confirming booking
public class ConfirmBookingRequest {
    private UUID bookingId;

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }
}
