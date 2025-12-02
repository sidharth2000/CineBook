package com.cinebook.command;

import java.time.LocalDateTime;
import java.util.UUID;

import com.cinebook.model.Booking;
import com.cinebook.model.BookingStatus;
import com.cinebook.model.PromoCode;
import com.cinebook.model.VoucherCode;
import com.cinebook.model.Customer;
import com.cinebook.repository.BookingRepository;
import com.cinebook.repository.BookingStatusRepository;
import com.cinebook.repository.PromoCodeRepository;
import com.cinebook.repository.VoucherCodeRepository;

public class ApplyDiscountCommand implements BookingCommand {

    private final BookingRepository bookingRepository;
    private final BookingStatusRepository bookingStatusRepository;
    private final PromoCodeRepository promoCodeRepository;
    private final VoucherCodeRepository voucherCodeRepository;

    private final UUID bookingId;         
    private final String discountCode;     
    private final Customer customer;

    private final int lockDurationMinutes = 5;
    
    private Booking booking; 

    public ApplyDiscountCommand(UUID bookingId,
                                String discountCode,
                                Customer customer,
                                BookingRepository bookingRepository,
                                BookingStatusRepository bookingStatusRepository,
                                PromoCodeRepository promoCodeRepository,
                                VoucherCodeRepository voucherCodeRepository) {
        this.bookingId = bookingId;
        this.discountCode = discountCode;
        this.customer = customer;
        this.bookingRepository = bookingRepository;
        this.bookingStatusRepository = bookingStatusRepository;
        this.promoCodeRepository = promoCodeRepository;
        this.voucherCodeRepository = voucherCodeRepository;
    }

    @Override
    public void execute() {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime lockExpiry = booking.getCreatedAt().plusMinutes(lockDurationMinutes);


        if (now.isAfter(lockExpiry)) {
            BookingStatus failureStatus = bookingStatusRepository.findByStatusName("FAILURE");
            booking.setStatus(failureStatus);
            booking.setModifiedAt(now);
            bookingRepository.save(booking);
            System.out.println("saveee");
            throw new RuntimeException("Booking session expired. Cannot apply discount.");
        }

        float originalTotalPrice = booking.getTotalPrice();
        float discountAmount = 0f;


        PromoCode promo = promoCodeRepository.findByCode(discountCode);
        if (promo != null) {
            if (now.isBefore(promo.getStartDate()) || now.isAfter(promo.getExpiryDate())) {
                throw new RuntimeException("Promo code is not valid at this time");
            }
            discountAmount = Math.min(originalTotalPrice * promo.getDiscountPercentage() / 100, 
                                      promo.getMaxDiscountAmount());
        } else {
            // Check if discountCode is a valid VoucherCode for this customer
            VoucherCode voucher = voucherCodeRepository.findByCode(discountCode);
            if (voucher == null || !voucher.getCustomer().getUserId().equals(customer.getUserId())) {
                throw new RuntimeException("Voucher code does not belong to this customer");
            }
            if (now.isAfter(voucher.getExpiryDate())) {
                throw new RuntimeException("Voucher code has expired");
            }
            discountAmount = Math.min(originalTotalPrice * voucher.getDiscountPercentage() / 100,
                                      voucher.getMaxDiscountAmount());
        }

        // Apply discount
        booking.setDiscountedAmount(discountAmount);
        booking.setTotalPrice(originalTotalPrice - discountAmount);
        booking.setModifiedAt(now);

        bookingRepository.save(booking);
        this.booking = booking;
        
    }

    @Override
    public void undo() {
        Booking persistedBooking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found for undo"));

        // Reset discount
        float originalTotalPrice = persistedBooking.getTotalPrice() +
            (persistedBooking.getDiscountedAmount() != null ? persistedBooking.getDiscountedAmount() : 0f);

        persistedBooking.setTotalPrice(originalTotalPrice);
        persistedBooking.setDiscountedAmount(0f);
        persistedBooking.setModifiedAt(LocalDateTime.now());

        bookingRepository.save(persistedBooking);
    }
    
    
    @Override
    public Booking getBooking() {
        return booking;
    }
}
