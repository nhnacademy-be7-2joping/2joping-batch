package com.nhnacademy.twojopingbatch.batch;

import com.nhnacademy.twojopingbatch.batch.processor.CouponProcessor;
import com.nhnacademy.twojopingbatch.mybatis.dto.MemberCouponDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CouponProcessorTest {

    private CouponProcessor couponProcessor;

    @BeforeEach
    void setUp() {
        // CouponProcessor 초기화
        couponProcessor = new CouponProcessor(123L); // 테스트용 couponId
    }

    @Test
    void testProcess_FirstTimeProcessing() {
        // Given
        Long memberId = 1L;

        // When
        MemberCouponDto result = couponProcessor.process(memberId);

        // Then
        assertNotNull(result); // 첫 처리이므로 반환값이 있어야 함
        assertEquals(123L, result.couponId());
        assertEquals(memberId, result.customerId());
        assertFalse(result.isUsed());
        assertNotNull(result.receiveTime());
        assertNotNull(result.invalidTime());
        assertTrue(result.invalidTime().isAfter(result.receiveTime()));
    }

    @Test
    void testProcess_AlreadyProcessed() {
        // Given
        Long memberId = 1L;

        // 첫 번째 처리
        couponProcessor.process(memberId);

        // When
        MemberCouponDto result = couponProcessor.process(memberId);

        // Then
        assertNull(result); // 이미 처리된 경우 null 반환
    }

    @Test
    void testProcess_MultipleIds() {
        // Given
        Long memberId1 = 1L;
        Long memberId2 = 2L;

        // When
        MemberCouponDto result1 = couponProcessor.process(memberId1);
        MemberCouponDto result2 = couponProcessor.process(memberId2);

        // Then
        assertNotNull(result1); // 첫 번째 ID 처리
        assertNotNull(result2); // 두 번째 ID 처리

        // 재처리 시 null 반환 확인
        assertNull(couponProcessor.process(memberId1));
        assertNull(couponProcessor.process(memberId2));
    }
}