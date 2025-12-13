/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * 
 * This is interface for the booking command - supports execute and undo and getBooking 
 * 
 */

package com.cinebook.command;

import com.cinebook.model.Booking;

public interface BookingCommand {
	void execute();

	void undo();

	Booking getBooking();
}
