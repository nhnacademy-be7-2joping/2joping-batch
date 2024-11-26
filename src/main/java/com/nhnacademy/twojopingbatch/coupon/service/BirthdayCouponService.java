package com.nhnacademy.twojopingbatch.coupon.service;

/**
 * BirthdayCouponService
 * 매월 생일자에게 생일 쿠폰을 발급하는 서비스 인터페이스입니다.
 * 각 생일자에 대한 쿠폰 발급 로직을 구현하는 메서드를 정의합니다.
 *
 * @since 1.0
 * @author Luha
 */
public interface BirthdayCouponService {
    /**
     * 월별 생일자에게 생일 쿠폰을 발급하는 메서드.
     * 현재 월에 해당하는 모든 생일자를 조회하여 쿠폰을 발급하는 작업을 수행합니다.
     */
    long issueBirthdayCoupon();
}
