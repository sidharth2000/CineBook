/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * 
 * This class is Concrete Creator for VoucherCode
 * 
 */

package com.cinebook.discountcodefactory;

import java.time.LocalDateTime;

import com.cinebook.model.Customer;
import com.cinebook.model.DiscountCode;
import com.cinebook.model.VoucherCode;
import com.cinebook.model.VoucherType;

// ConcreateCreator for VoucherCode
public class VoucherCodeCreator extends DiscountCodeCreator {

	private final Customer customer;
	private final VoucherType voucherType;

	public VoucherCodeCreator(Customer customer, VoucherType voucherType) {
		this.customer = customer;
		this.voucherType = voucherType;
	}

	@Override
	protected void validate() {
		if (!voucherType.getIsActive()) {
			throw new RuntimeException("Voucher type is inactive");
		}
		if (customer.getLoyaltyPoints() < voucherType.getLoyaltyPointsCost()) {
			throw new RuntimeException("Insufficient points to purchase voucher");
		}
	}

	@Override
	protected DiscountCode createDiscountCode() {

		VoucherCode code = new VoucherCode();

		code.setCustomer(customer);
		code.setVoucherType(voucherType);

		code.setDiscountPercentage(voucherType.getDiscountPercentage());
		code.setMaxDiscountAmount(voucherType.getMaxDiscountAmount());

		code.setCode(generateUniqueCode(voucherType.getVoucherName()));

		LocalDateTime now = LocalDateTime.now();
		code.setPurchaseDate(now);
		code.setExpiryDate(now.plusDays(voucherType.getValidityPeriodDays()));

		return code;
	}

	private String generateUniqueCode(String prefix) {
		return prefix + "-" + System.currentTimeMillis();
	}

	@Override
	protected void postProcess(DiscountCode code) {
		customer.setLoyaltyPoints(customer.getLoyaltyPoints() - voucherType.getLoyaltyPointsCost());
	}
}
