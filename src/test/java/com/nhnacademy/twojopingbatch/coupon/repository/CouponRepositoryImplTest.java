package com.nhnacademy.twojopingbatch.coupon.repository;

import com.nhnacademy.twojopingbatch.coupon.entity.QCoupon;
import com.nhnacademy.twojopingbatch.coupon.repository.coupon.impl.CouponRepositoryImpl;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class CouponRepositoryImplTest {

    private JPAQueryFactory queryFactory;
    private CouponRepositoryImpl couponRepository;

    @BeforeEach
    void setUp() {
        queryFactory = mock(JPAQueryFactory.class);
        couponRepository = new CouponRepositoryImpl(queryFactory);
    }

    @Test
    void testFindIdByName() {
        // Given
        String couponName = "Test Coupon";
        Long expectedId = 1L;

        // Mock QueryDSL 동작 설정
        JPAQuery<Long> mockQuery = mock(JPAQuery.class);
        when(queryFactory.select(QCoupon.coupon.id))
                .thenReturn(mockQuery);
        when(mockQuery.from(QCoupon.coupon))
                .thenReturn(mockQuery);
        when(mockQuery.where((Predicate) any()))
                .thenReturn(mockQuery);
        when(mockQuery.fetchOne())
                .thenReturn(expectedId);

        // When
        Long result = couponRepository.findIdByName(couponName);

        // Then
        assertEquals(expectedId, result);

        // Verify 호출 순서
        verify(queryFactory).select(QCoupon.coupon.id);
        verify(mockQuery).from(QCoupon.coupon);
        verify(mockQuery).where(QCoupon.coupon.name.eq(couponName));
        verify(mockQuery).fetchOne();
    }

    @Test
    void testFindIdByName_NotFound() {
        // Given
        String couponName = "NonExisting Coupon";

        // Mock QueryDSL 동작 설정
        JPAQuery<Long> mockQuery = mock(JPAQuery.class);
        when(queryFactory.select(QCoupon.coupon.id))
                .thenReturn(mockQuery);
        when(mockQuery.from(QCoupon.coupon))
                .thenReturn(mockQuery);
        when(mockQuery.where((Predicate) any()))
                .thenReturn(mockQuery);
        when(mockQuery.fetchOne())
                .thenReturn(null);

        // When
        Long result = couponRepository.findIdByName(couponName);

        // Then
        assertNull(result);

        // Verify 호출 순서
        verify(queryFactory).select(QCoupon.coupon.id);
        verify(mockQuery).from(QCoupon.coupon);
        verify(mockQuery).where(QCoupon.coupon.name.eq(couponName));
        verify(mockQuery).fetchOne();
    }
}