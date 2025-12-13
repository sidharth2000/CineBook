/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * 
 * Concrete Command to apply discount after locking seats
 * execute() applies the PROMO or VOUCHER code applies the discount 
 * undo() restore the original price with restoring the PROMO or VOUCHER code
 */

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

	public ApplyDiscountCommand(UUID bookingId, String discountCode, Customer customer,
			BookingRepository bookingRepository, BookingStatusRepository bookingStatusRepository,
			PromoCodeRepository promoCodeRepository, VoucherCodeRepository voucherCodeRepository) {
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
		booking = bookingRepository.findById(bookingId).orElseThrow(() -> new RuntimeException("Booking not found"));

		LocalDateTime now = LocalDateTime.now();
		LocalDateTime lockExpiry = booking.getCreatedAt().plusMinutes(lockDurationMinutes);

		if (now.isAfter(lockExpiry)) {
			BookingStatus failureStatus = bookingStatusRepository.findByStatusName("FAILURE");
			booking.setStatus(failureStatus);
			booking.setModifiedAt(now);
			bookingRepository.save(booking);
			throw new RuntimeException("Booking session expired. Cannot apply discount. Refresh the page");
		}

		if (booking.getPromocodeUsed() != null) {
			throw new RuntimeException("A promo code is already applied to this booking");
		}

		if (booking.getVoucherApplied() != null) {
			throw new RuntimeException("A voucher code is already applied to this booking");
		}

		float originalTotalPrice = booking.getTotalPrice();
		float discountAmount = 0f;

		PromoCode promo = promoCodeRepository.findByCode(discountCode);
		if (promo != null) {
			if (now.isBefore(promo.getStartDate()) || now.isAfter(promo.getExpiryDate())) {
				throw new RuntimeException("Promo code is not valid at this time");
			}

			long usedCount = bookingRepository.countByCustomerAndPromocodeUsed(customer, promo);
			if (usedCount >= promo.getMaxUsePerCustomer()) {
				throw new RuntimeException("Promo code already used maximum times by this customer");
			}

			discountAmount = Math.min(originalTotalPrice * promo.getDiscountPercentage() / 100,
					promo.getMaxDiscountAmount());

			booking.setPromocodeUsed(promo);

		} else {
			VoucherCode voucher = voucherCodeRepository.findByCode(discountCode);
			if (voucher == null || !voucher.getCustomer().getUserId().equals(customer.getUserId())) {
				throw new RuntimeException("Voucher code does not belong to this customer");
			}

			long usedCount = bookingRepository.countByVoucherApplied(voucher);
			if (usedCount > 0) {
				throw new RuntimeException("Voucher code already used in another booking");
			}

			if (now.isAfter(voucher.getExpiryDate())) {
				throw new RuntimeException("Voucher code has expired");
			}

			discountAmount = Math.min(originalTotalPrice * voucher.getDiscountPercentage() / 100,
					voucher.getMaxDiscountAmount());

			booking.setVoucherApplied(voucher);
		}

		booking.setDiscountedAmount(discountAmount);
		booking.setTotalPrice(originalTotalPrice - discountAmount);
		booking.setModifiedAt(now);

		bookingRepository.save(booking);
	}

	@Override
	public void undo() {
		booking = bookingRepository.findById(bookingId)
				.orElseThrow(() -> new RuntimeException("Booking not found for undo"));

		float originalTotalPrice = booking.getTotalPrice()
				+ (booking.getDiscountedAmount() != null ? booking.getDiscountedAmount() : 0f);

		booking.setTotalPrice(originalTotalPrice);
		booking.setDiscountedAmount(0f);
		booking.setPromocodeUsed(null);
		booking.setVoucherApplied(null);
		booking.setModifiedAt(LocalDateTime.now());

		bookingRepository.save(booking);
	}

	@Override
	public Booking getBooking() {
		return booking;
	}
}
