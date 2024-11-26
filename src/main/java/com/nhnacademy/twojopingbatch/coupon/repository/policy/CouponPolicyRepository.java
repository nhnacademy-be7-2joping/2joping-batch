package com.nhnacademy.twojopingbatch.coupon.repository.policy;

import com.nhnacademy.twojopingbatch.coupon.entity.CouponPolicy;
import com.nhnacademy.twojopingbatch.coupon.enums.CouponPolicyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * CouponPolicyRepository
 * 쿠폰 정책 엔티티의 CRUD 기능을 제공하는 JPA 리포지토리 인터페이스입니다.
 * 쿠폰 정책의 타입별 조회를 위한 추가 메서드를 정의하여 특정 정책을 가져오는 데 사용됩니다.
 *
 * @see JpaRepository
 * @see CouponPolicy
 * @since 1.0
 * @version 1.0
 */
public interface CouponPolicyRepository  extends JpaRepository<CouponPolicy, Long>{

    /**
     * 주어진 타입의 쿠폰 정책을 조회하는 메서드.
     *
     * @param type 조회할 쿠폰 정책의 타입
     * @return 주어진 타입의 쿠폰 정책을 포함한 Optional 객체, 해당 타입의 정책이 없으면 빈 Optional 반환
     */
    Optional<CouponPolicy> findByType(CouponPolicyType type);

}
