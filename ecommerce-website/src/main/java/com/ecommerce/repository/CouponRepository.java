package com.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.model.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Coupon findByCode(String code);
}
