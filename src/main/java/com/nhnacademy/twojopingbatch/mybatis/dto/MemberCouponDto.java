package com.nhnacademy.twojopingbatch.mybatis.dto;


import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record MemberCouponDto(
        Long couponId,
        Long customerId,
        LocalDateTime receiveTime,
        LocalDateTime invalidTime,
        Boolean isUsed) { }