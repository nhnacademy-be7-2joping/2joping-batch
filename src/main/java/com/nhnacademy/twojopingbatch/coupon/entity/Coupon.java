package com.nhnacademy.twojopingbatch.coupon.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;



/**
 * Coupon 엔티티 클래스
 * 쿠폰 정보와 쿠폰 정책을 관리하는 클래스입니다.
 * 특정 정책을 기반으로 생성된 쿠폰의 정보(생성일, 만료일, 수량 등)를 포함합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Entity
@Table(name = "coupon")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "coupon_id")
        private Long id;

        @Column(length = 30, unique = true)
        private String name;

        private LocalDate createdAt;

        private LocalDate expiredAt;

        private Integer quantity;

        @ManyToOne
        @JoinColumn(name = "coupon_policy_id", referencedColumnName = "coupon_policy_id")
        @Setter
        private CouponPolicy couponPolicy;


}