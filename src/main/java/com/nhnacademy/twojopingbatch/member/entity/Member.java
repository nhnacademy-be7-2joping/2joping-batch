package com.nhnacademy.twojopingbatch.member.entity;



import com.nhnacademy.twojopingbatch.coupon.entity.MemberCoupon;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * Member 엔티티 클래스
 * 회원 정보를 관리하는 클래스입니다. 회원의 가입일, 생일, 누적 구매 금액 등의 정보를 저장하며,
 * 특정 멤버 등급과 연관되어 있습니다. 회원의 등급은 구매 금액 등의 기준에 따라 달라질 수 있습니다.
 *
 * @author Luha
 * @since 1.0
 */
@Entity
@Table(name = "member")
@PrimaryKeyJoinColumn(name = "customer_id")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends Customer {


        private LocalDate birthday;

        private LocalDate joinDate;
        private Integer birthdayMonth; // 추가된 컬럼 매핑

        private Integer accPurchase;

        @ManyToOne
        @JoinColumn(name = "member_tier_id")
        private MemberTier memberTier;


        @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
        private List<MemberCoupon> memberCoupons;
}