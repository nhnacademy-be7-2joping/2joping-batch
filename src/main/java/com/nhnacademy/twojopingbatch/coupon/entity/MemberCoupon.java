package com.nhnacademy.twojopingbatch.coupon.entity;


import com.nhnacademy.twojopingbatch.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * MemberCoupon 엔티티 클래스
 * 회원의 쿠폰 발급 및 사용 내역을 관리하는 클래스입니다.
 */
@Entity
@Table(name = "member_coupon")
@Getter
@NoArgsConstructor
public class MemberCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long couponUsageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Member member;

    private LocalDateTime receiveTime;

    private LocalDateTime invalidTime;

    private boolean isUsed;

    private LocalDateTime usedDate;


}