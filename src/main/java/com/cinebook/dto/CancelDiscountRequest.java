package com.cinebook.dto;

import java.util.UUID;

public class CancelDiscountRequest {

    private UUID bookingId;

    public CancelDiscountRequest() {}

    public CancelDiscountRequest(UUID bookingId) {
        this.bookingId = bookingId;
    }

    public UUID getBookingId() {
        return bookingId;
    }

    public void setBookingId(UUID bookingId) {
        this.bookingId = bookingId;
    }
}
