package com.nhnacademy.twojopingbatch.coupon.repository.coupon;

import com.nhnacademy.twojopingbatch.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * CouponRepository
 * 쿠폰 엔티티의 CRUD 및 추가적인 커스텀 조회 기능을 제공하는 JPA 리포지토리 인터페이스입니다.
 * Spring Data JPA의 JpaRepository를 상속하며, QueryDSL 기반의 커스텀 리포지토리인 CouponQuerydslRepository도 함께 상속합니다.
 *
 * @see JpaRepository
 * @see CouponQuerydslRepository
 * @see Coupon
 * @since 1.0
 * @version 1.0
 */
public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponQuerydslRepository {
    /**
     * 주어진 이름의 쿠폰이 존재하는지 여부를 확인하는 메서드.
     *
     * @param name 확인할 쿠폰의 이름
     * @return 쿠폰이 존재하면 true, 존재하지 않으면 false 반환
     */
    boolean existsByName(String name);

}
