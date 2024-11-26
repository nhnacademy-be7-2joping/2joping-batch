package com.nhnacademy.twojopingbatch.mybatis.dto;


import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MemberCouponDtoTest {

    @Test
    void testGeneratedMethods() {
        // Given
        MemberCouponDto dto = new MemberCouponDto(1L, 123L, LocalDateTime.now(), LocalDateTime.now().plusDays(30), false);

        // Test toString
        String toStringOutput = dto.toString();
        assertNotNull(toStringOutput);

        // Test equals and hashCode
        MemberCouponDto sameDto = new MemberCouponDto(1L, 123L, dto.receiveTime(), dto.invalidTime(), false);
        assertEquals(dto, sameDto);
        assertEquals(dto.hashCode(), sameDto.hashCode());
    }
    @Test
    void testMemberCouponDto() {
        // Given
        Long couponId = 1L;
        Long customerId = 123L;
        LocalDateTime receiveTime = LocalDateTime.now();
        LocalDateTime invalidTime = receiveTime.plusDays(30);
        Boolean isUsed = false;

        // When
        MemberCouponDto dto = new MemberCouponDto(couponId, customerId, receiveTime, invalidTime, isUsed);

        // Then
        assertEquals(couponId, dto.couponId());
        assertEquals(customerId, dto.customerId());
        assertEquals(receiveTime, dto.receiveTime());
        assertEquals(invalidTime, dto.invalidTime());
        assertEquals(isUsed, dto.isUsed());
    }
    @Test
    void testBuilder() {
        // Given
        Long couponId = 1L;
        Long customerId = 123L;
        LocalDateTime receiveTime = LocalDateTime.now();
        LocalDateTime invalidTime = receiveTime.plusDays(30);
        Boolean isUsed = false;

        // When
        MemberCouponDto dto = MemberCouponDto.builder()
                .couponId(couponId)
                .customerId(customerId)
                .receiveTime(receiveTime)
                .invalidTime(invalidTime)
                .isUsed(isUsed)
                .build();

        // Then
        assertEquals(couponId, dto.couponId());
        assertEquals(customerId, dto.customerId());
        assertEquals(receiveTime, dto.receiveTime());
        assertEquals(invalidTime, dto.invalidTime());
        assertEquals(isUsed, dto.isUsed());
    }
    @Test
    void testBuilderMethod() {
        // Test 빌더 인스턴스 생성
        MemberCouponDto.MemberCouponDtoBuilder builder = MemberCouponDto.builder()
                .couponId(1L)
                .customerId(123L)
                .receiveTime(LocalDateTime.now())
                .invalidTime(LocalDateTime.now().plusDays(30))
                .isUsed(false);

        assertNotNull(builder); // 빌더가 null이 아닌지 확인
    }

}