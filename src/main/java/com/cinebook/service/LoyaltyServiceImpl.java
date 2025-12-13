/**
 * @author Sidharthan Jayavelu
 * 
 * 
 */

package com.cinebook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cinebook.discountcodefactory.VoucherCodeCreator;
import com.cinebook.model.Customer;
import com.cinebook.model.VoucherCode;
import com.cinebook.model.VoucherType;
import com.cinebook.repository.CustomerRepository;
import com.cinebook.repository.VoucherCodeRepository;
import com.cinebook.repository.VoucherTypeRepository;

import jakarta.transaction.Transactional;

@Service
public class LoyaltyServiceImpl implements LoyaltyService {

	@Autowired
	VoucherTypeRepository voucherTypeRepository;
	@Autowired
	CustomerRepository customerRepository;
	@Autowired
	VoucherCodeRepository voucherCodeRepository;

	public LoyaltyServiceImpl(VoucherTypeRepository voucherTypeRepository, CustomerRepository customerRepository,
			VoucherCodeRepository voucherCodeRepository) {
		this.voucherTypeRepository = voucherTypeRepository;
		this.customerRepository = customerRepository;
		this.voucherCodeRepository = voucherCodeRepository;
	}

	@Override
	public List<VoucherType> getAllVoucherTypes() {
		return voucherTypeRepository.findAll().stream().filter(VoucherType::getIsActive).toList();
	}

	@Override
	@Transactional
	public VoucherCode purchaseVoucher(String customerEmail, Integer voucherTypeId) {
		Customer customer = customerRepository.findByEmail(customerEmail);

		VoucherType voucherType = voucherTypeRepository.findById(voucherTypeId)
				.orElseThrow(() -> new RuntimeException("Voucher type not found"));

		if (customer.getLoyaltyPoints() < voucherType.getLoyaltyPointsCost()) {
			throw new RuntimeException("Insufficient loyalty points");
		}

		VoucherCodeCreator creator = new VoucherCodeCreator(customer, voucherType);
		VoucherCode voucherCode = (VoucherCode) creator.generateCode();

		customer.setLoyaltyPoints(customer.getLoyaltyPoints() - voucherType.getLoyaltyPointsCost());
		customerRepository.save(customer);

		return voucherCodeRepository.save(voucherCode);
	}

}
