package com.nhnacademy.twojopingbatch.coupon.repository;

import com.nhnacademy.twojopingbatch.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
