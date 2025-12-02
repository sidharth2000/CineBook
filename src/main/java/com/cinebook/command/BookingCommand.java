package com.cinebook.command;

import com.cinebook.model.Booking;

public interface BookingCommand {
    void execute();
    void undo();
	Booking getBooking();
}
