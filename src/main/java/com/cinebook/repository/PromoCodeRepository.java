package com.cinebook.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cinebook.model.PromoCode;

public interface PromoCodeRepository extends JpaRepository<PromoCode, Integer> {

	PromoCode findByCode(String discountCode);
}
