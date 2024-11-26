package com.nhnacademy.twojopingbatch.coupon.service.impl;


import com.nhnacademy.twojopingbatch.coupon.entity.Coupon;
import com.nhnacademy.twojopingbatch.coupon.entity.CouponPolicy;
import com.nhnacademy.twojopingbatch.coupon.enums.CouponPolicyType;
import com.nhnacademy.twojopingbatch.coupon.enums.DiscountType;
import com.nhnacademy.twojopingbatch.coupon.repository.coupon.CouponRepository;
import com.nhnacademy.twojopingbatch.coupon.repository.policy.CouponPolicyRepository;
import com.nhnacademy.twojopingbatch.mybatis.mapper.MemberCouponMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class BirthdayCouponServiceImplTest {

    private CouponPolicyRepository couponPolicyRepository;
    private CouponRepository couponRepository;
    private MemberCouponMapper memberCouponMapper;
    private BirthdayCouponServiceImpl birthdayCouponService;

    @BeforeEach
    void setUp() {
        couponPolicyRepository = mock(CouponPolicyRepository.class);
        couponRepository = mock(CouponRepository.class);
        memberCouponMapper = mock(MemberCouponMapper.class);

        birthdayCouponService = new BirthdayCouponServiceImpl(couponPolicyRepository, couponRepository, memberCouponMapper);
    }

    @Test
    void testIssueBirthdayCoupon_NewCoupon() {
        // Given
        LocalDate today = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(today);
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();
        int month = today.getMonthValue();

        String couponName = month + "월 생일 쿠폰";

        CouponPolicy birthdayPolicy = new CouponPolicy(
                1L,
                "Birthday Policy",
                DiscountType.PERCENT,
                10,
                100,
                30,
                "생일자 전용 10% 할인",
                50,
                true,
                CouponPolicyType.BIRTHDAY
        );

        when(couponPolicyRepository.findByType(CouponPolicyType.BIRTHDAY))
                .thenReturn(Optional.of(birthdayPolicy));

        when(couponRepository.existsByName(couponName)).thenReturn(false);

        Coupon savedCoupon = new Coupon(1L, couponName, today, lastDayOfMonth, null, birthdayPolicy);
        when(couponRepository.save(any(Coupon.class))).thenReturn(savedCoupon);

        // When
        long result = birthdayCouponService.issueBirthdayCoupon();

        // Then
        assertEquals(1L, result);

        ArgumentCaptor<Coupon> couponCaptor = ArgumentCaptor.forClass(Coupon.class);
        verify(couponRepository).save(couponCaptor.capture());
        Coupon capturedCoupon = couponCaptor.getValue();

        assertEquals(couponName, capturedCoupon.getName());
        assertEquals(today, capturedCoupon.getCreatedAt());
        assertEquals(lastDayOfMonth, capturedCoupon.getExpiredAt());
        assertEquals(birthdayPolicy, capturedCoupon.getCouponPolicy());
    }

    @Test
    void testIssueBirthdayCoupon_ExistingCoupon() {
        // Given
        int month = LocalDate.now().getMonthValue();
        String couponName = month + "월 생일 쿠폰";

        CouponPolicy birthdayPolicy = new CouponPolicy(
                1L,
                "Birthday Policy",
                DiscountType.PERCENT,
                10,
                100,
                30,
                "생일자 전용 10% 할인",
                50,
                true,
                CouponPolicyType.BIRTHDAY
        );

        when(couponPolicyRepository.findByType(CouponPolicyType.BIRTHDAY))
                .thenReturn(Optional.of(birthdayPolicy));

        when(couponRepository.existsByName(couponName)).thenReturn(true);
        when(couponRepository.findIdByName(couponName)).thenReturn(1L);

        // When
        long result = birthdayCouponService.issueBirthdayCoupon();

        // Then
        assertEquals(1L, result);
        verify(couponRepository, never()).save(any(Coupon.class));
    }

    @Test
    void testIssueBirthdayCoupon_PolicyNotFound() {
        // Given
        when(couponPolicyRepository.findByType(CouponPolicyType.BIRTHDAY))
                .thenReturn(Optional.empty());

        // When & Then
        IllegalStateException exception =
                assertThrows(IllegalStateException.class, () -> birthdayCouponService.issueBirthdayCoupon());
        assertEquals("Birthday coupon policy not found", exception.getMessage());
    }
}