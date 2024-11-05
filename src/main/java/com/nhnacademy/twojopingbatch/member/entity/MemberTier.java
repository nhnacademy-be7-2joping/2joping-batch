package com.nhnacademy.twojopingbatch.member.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * MemberTier 엔티티 클래스
 * 회원의 등급 정보를 관리하는 클래스입니다. 각 등급은 고유 ID와 이름, 적립률, 프로모션 기준 등을 가지고 있으며,
 * 회원의 누적 구매 금액 등에 따라 등급이 달라질 수 있습니다.
 *
 * @author Luha
 * @since 1.0
 */
@Entity
@Table(name = "member_tier")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberTier {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long memberTierId;

        @Column(length = 20)
        private String name;

        private Boolean status;

        private Byte accRate;

        private Integer promotion;
}