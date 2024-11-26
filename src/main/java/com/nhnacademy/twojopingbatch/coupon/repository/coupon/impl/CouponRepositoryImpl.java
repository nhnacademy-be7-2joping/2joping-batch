package com.nhnacademy.twojopingbatch.coupon.repository.coupon.impl;



import com.nhnacademy.twojopingbatch.coupon.repository.coupon.CouponQuerydslRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.nhnacademy.twojopingbatch.coupon.entity.QCoupon.coupon;

/**
 * CouponRepositoryImpl
 * 이 클래스는 CouponQuerydslRepository 인터페이스의 구현체로,
 * QueryDSL을 사용하여 쿠폰 정보를 데이터베이스에서 조회하는 기능을 제공합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findIdByName(String name) {
        return queryFactory
                .select(coupon.id)
                .from(coupon)
                .where(coupon.name.eq(name))
                .fetchOne();
    }
}