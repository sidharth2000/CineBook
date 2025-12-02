package com.cinebook.observers;

import java.util.ArrayList;
import java.util.List;

import com.cinebook.model.Booking;
import com.cinebook.notification.Notification;
import com.cinebook.notification.NotificationFactory;
import com.cinebook.notification.NotificationType;

public class CustomerNotificationObserver implements BookingObserver {


    public CustomerNotificationObserver() {

    }

    @Override
    public void update(Booking booking) {

        Notification emailNotification = NotificationFactory.createNotification(NotificationType.EMAIL);

        String customerEmail = booking.getCustomer().getEmail();

        switch (booking.getStatus().getStatusName()) {

            case "SUCCESS":
            	// logic to crate the ticket PDF
            	byte[] ticketPdf = null;
            	emailNotification.addAttachment(ticketPdf);

                emailNotification.send(
                        customerEmail,
                        "Your booking was successful! Booking ID: " + booking.getBookingId()
                );

                System.out.println("Success email sent to: " + customerEmail);
                break;

            case "CANCELLED":

                emailNotification.send(
                        customerEmail,
                        "Your booking has been cancelled. Booking ID: " + booking.getBookingId()
                );

                System.out.println("Cancellation email sent to: " + customerEmail);
                break;

            default:
                System.out.println("No notification action required for status: " + booking.getStatus());
        }
    }
}