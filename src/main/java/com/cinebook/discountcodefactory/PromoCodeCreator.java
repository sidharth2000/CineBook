/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * 
 * This class is Concrete Creator for PromoCode
 * 
 */

package com.cinebook.discountcodefactory;

import java.time.LocalDateTime;

import com.cinebook.model.DiscountCode;
import com.cinebook.model.PromoCode;

//ConcreateCreator for PromoCode
public class PromoCodeCreator extends DiscountCodeCreator {

	private final String prefix;
	private final Float discountPercentage;
	private final Float maxDiscountAmount;
	private final LocalDateTime startDate;
	private final LocalDateTime expiryDate;
	private final Integer maxUsePerCustomer;

	public PromoCodeCreator(String prefix, Float discountPercentage, Float maxDiscountAmount, LocalDateTime startDate,
			LocalDateTime expiryDate, Integer maxUsePerCustomer) {
		this.prefix = prefix;
		this.discountPercentage = discountPercentage;
		this.maxDiscountAmount = maxDiscountAmount;
		this.startDate = startDate;
		this.expiryDate = expiryDate;
		this.maxUsePerCustomer = maxUsePerCustomer;
	}

	@Override
	protected void validate() {
		if (expiryDate.isBefore(startDate)) {
			throw new RuntimeException("Promo expiry date cannot be before start date");
		}
	}

	@Override
	protected DiscountCode createDiscountCode() {

		PromoCode code = new PromoCode();

		code.setCode(generateUniqueCode(prefix));
		code.setDiscountPercentage(discountPercentage);
		code.setMaxDiscountAmount(maxDiscountAmount);
		code.setStartDate(startDate);
		code.setExpiryDate(expiryDate);
		code.setMaxUsePerCustomer(maxUsePerCustomer);

		return code;
	}

	private String generateUniqueCode(String prefix) {
		return prefix;
	}
}
