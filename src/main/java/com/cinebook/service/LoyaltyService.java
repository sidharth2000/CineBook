/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.service;

import java.util.List;

import com.cinebook.model.VoucherCode;
import com.cinebook.model.VoucherType;

public interface LoyaltyService {
	List<VoucherType> getAllVoucherTypes();

	VoucherCode purchaseVoucher(String customerEmail, Integer voucherTypeId);
}
