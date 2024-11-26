package com.nhnacademy.twojopingbatch.batch.processor;

import com.nhnacademy.twojopingbatch.mybatis.dto.MemberCouponDto;
import jakarta.annotation.Nonnull;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * CouponProcessor
 * 스프링 배치에서 사용되는 ItemProcessor 구현체입니다.
 * 회원 ID(Long)를 입력으로 받아, 해당 회원에게 발급할 쿠폰 정보를 포함한 MemberCouponDto 객체를 반환합니다.
 * 처리 중복을 방지하기 위해 이미 처리된 회원 ID를 저장하며, 중복된 ID는 null을 반환하여 스킵합니다.
 *
 * @see ItemProcessor
 * @see MemberCouponDto
 * @since 1.0
 * @author Luha
 */
@Component
@StepScope
public class CouponProcessor implements ItemProcessor<Long, MemberCouponDto> {

    private final Long couponId;
    private final Set<Long> processedIds = Collections.synchronizedSet(new HashSet<>());

    public CouponProcessor(@Value("#{jobParameters['couponId']}") Long couponId) {
        this.couponId = couponId;
    }

    /**
     * 입력 회원 ID를 처리하여 MemberCouponDto 객체를 생성합니다.
     * 중복된 ID는 처리하지 않고 null을 반환하여 스킵합니다.
     *
     * @param memberId 처리할 회원 ID
     * @return 처리된 회원의 MemberCouponDto 객체 또는 null(중복된 경우)
     */
    @Override
    public MemberCouponDto process(@Nonnull Long memberId) {
        if (processedIds.contains(memberId)) {
            return null; // 이미 처리된 경우 스킵
        }
        processedIds.add(memberId);

        return MemberCouponDto.builder()
                .couponId(couponId)
                .customerId(memberId)
                .receiveTime(LocalDateTime.now())
                .invalidTime(LocalDateTime.now().plusMonths(1))
                .isUsed(false)
                .build();
    }
}