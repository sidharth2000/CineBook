package com.cinebook.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.VoucherCode;

public interface VoucherCodeRepository extends JpaRepository<VoucherCode,Integer> {

	VoucherCode findByCode(String discountCode);

}
