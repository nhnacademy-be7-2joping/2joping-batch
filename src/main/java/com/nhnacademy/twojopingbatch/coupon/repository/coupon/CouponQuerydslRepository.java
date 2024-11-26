package com.nhnacademy.twojopingbatch.coupon.repository.coupon;

/**
 * CouponQuerydslRepository
 * 쿠폰 정보를 조회하는 QueryDSL 기반의 커스텀 리포지토리 인터페이스입니다.
 * 구현체에서 QueryDSL을 통해 특정 조건에 맞는 쿠폰 정보를 데이터베이스에서 조회하는 기능을 제공합니다.
 *
 * @author Luha
 * @since 1.0
 */
public interface CouponQuerydslRepository {

    /**
     * 쿠폰 이름으로 쿠폰 ID를 조회하는 메서드.
     *
     * @param name 조회할 쿠폰의 이름
     * @return 해당 이름의 쿠폰 ID(Long 형식) 또는 해당 이름의 쿠폰이 없으면 null 반환
     */
    Long findIdByName(String name);

}
