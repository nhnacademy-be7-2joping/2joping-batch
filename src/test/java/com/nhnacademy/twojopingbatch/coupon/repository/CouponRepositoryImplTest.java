package com.nhnacademy.twojopingbatch.coupon.repository;

import com.nhnacademy.twojopingbatch.coupon.entity.Coupon;
import com.nhnacademy.twojopingbatch.coupon.repository.coupon.CouponRepository;
import com.nhnacademy.twojopingbatch.coupon.repository.coupon.impl.CouponRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * CouponRepositoryImplTest
 *
 * 이 클래스는 CouponRepositoryImpl의 findIdByName 메서드를 테스트합니다.
 * 쿠폰이 데이터베이스에 올바르게 저장되고 조회되는지 확인하는 테스트입니다.
 *
 * @author Luha
 * @since 1.0
 */
@SpringBootTest
class CouponRepositoryImplTest {

    @Autowired
    private CouponRepositoryImpl couponRepository;

    @Autowired
    private CouponRepository couponJpaRepository;

    /**
     * findIdByName 메서드 테스트.
     *
     * Given: 데이터베이스에 쿠폰을 저장.
     * When: findIdByName 메서드를 사용하여 쿠폰 ID를 조회.
     * Then: 저장한 쿠폰의 ID와 조회한 ID가 일치하는지 검증.
     */
    @Test
    @Transactional
    void testFindIdByName() {
        // Given: 데이터 준비
        Coupon coupon = new Coupon(null, "Test Coupon", LocalDate.now(), LocalDate.now().plusDays(10), 100, null);
        couponJpaRepository.save(coupon);

        // When: 쿼리 메서드 호출
        Long couponId = couponRepository.findIdByName("Test Coupon");

        // Then: 결과 검증
        assertNotNull(couponId);
        assertEquals(coupon.getId(), couponId);
    }
}