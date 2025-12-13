/**
 * @author Sidharthan Jayavelu
 * 
 * Description:
 * Customer can use their earned loyaltypoints to buy new VoucherCodes by selecting a vouchertype like a 
 * marketplace
 * 
 * 
 */

package com.cinebook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cinebook.dto.ApiResponse;
import com.cinebook.dto.PurchaseVoucherRequest;
import com.cinebook.model.VoucherCode;
import com.cinebook.model.VoucherType;
import com.cinebook.service.LoyaltyService;

@RestController
@RequestMapping("/loyalty")
public class LoyaltyController {

	@Autowired
	private LoyaltyService loyaltyService;

	// API to get all voucher types
	@GetMapping("/voucher-types")
	public ResponseEntity<ApiResponse<List<VoucherType>>> getAllVoucherTypes() {
		List<VoucherType> voucherTypes = loyaltyService.getAllVoucherTypes();
		return ResponseEntity
				.ok(new ApiResponse<>("success", voucherTypes, "Available voucher types retrieved successfully"));
	}

	// API to purchase a voucher with loyalty point
	@PostMapping("/purchase-voucher")
	public ResponseEntity<ApiResponse<VoucherCode>> purchaseVoucher(@RequestBody PurchaseVoucherRequest request) {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String customerEmail = userDetails.getUsername();

		VoucherCode voucherCode = loyaltyService.purchaseVoucher(customerEmail, request.getVoucherTypeId());

		return ResponseEntity.ok(new ApiResponse<>("success", voucherCode, "Voucher purchased successfully"));
	}
}
