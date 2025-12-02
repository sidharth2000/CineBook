package com.cinebook.observers;

import com.cinebook.model.Booking;

public interface BookingObserver {
    void update(Booking booking);
}
