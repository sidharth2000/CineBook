package com.cinebook.observers;	

import com.cinebook.model.Booking;
import com.cinebook.model.Customer;
import com.cinebook.repository.CustomerRepository;

public class LoyaltyPointsObserver implements BookingObserver {

    private final CustomerRepository customerRepository;

    public LoyaltyPointsObserver(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public void update(Booking booking) {

        Customer customer = booking.getCustomer();
        int points = booking.getLoyaltyPointsEarned();

        String status = booking.getStatus().getStatusName().toUpperCase();

        switch (status) {

            case "SUCCESS":
                customer.setLoyaltyPoints(
                        customer.getLoyaltyPoints() + points
                );
                System.out.println("Added " + points + " loyalty points.");
                break;

            case "CANCELLED":
                customer.setLoyaltyPoints(
                        Math.max(0, customer.getLoyaltyPoints() - points)
                );
                System.out.println("Reverted " + points + " loyalty points due to cancellation.");
                break;

            default:
                System.out.println("No loyalty update for status: " + status);
                return;
        }

        customerRepository.save(customer);
    }
}
