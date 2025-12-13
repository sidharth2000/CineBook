/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.dto;

public class PurchaseVoucherRequest {
	private Integer customerId;
	private Integer voucherTypeId;

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getVoucherTypeId() {
		return voucherTypeId;
	}

	public void setVoucherTypeId(Integer voucherTypeId) {
		this.voucherTypeId = voucherTypeId;
	}
}