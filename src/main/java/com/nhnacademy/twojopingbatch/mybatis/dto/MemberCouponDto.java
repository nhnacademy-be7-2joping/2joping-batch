package com.nhnacademy.twojopingbatch.mybatis.dto;


import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

/**
 * MemberCouponDto 클래스
 * MyBatis를 통한 쿠폰 사용 정보 저장을 위한 DTO 클래스입니다.
 * 회원이 특정 쿠폰을 언제 받았고 유효 기간은 언제까지인지, 사용 여부 등을 관리합니다.
 * @since 1.0
 * @author Luha
 */
public record MemberCouponDto(
        Long couponId,
        Long customerId,
        LocalDateTime receiveTime,
        LocalDateTime invalidTime,
        Boolean isUsed
) {}