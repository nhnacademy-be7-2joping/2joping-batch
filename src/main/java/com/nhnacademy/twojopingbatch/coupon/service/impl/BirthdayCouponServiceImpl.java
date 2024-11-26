package com.nhnacademy.twojopingbatch.coupon.service.impl;

import com.nhnacademy.twojopingbatch.coupon.entity.Coupon;
import com.nhnacademy.twojopingbatch.coupon.entity.CouponPolicy;
import com.nhnacademy.twojopingbatch.coupon.enums.CouponPolicyType;
import com.nhnacademy.twojopingbatch.coupon.repository.coupon.CouponRepository;
import com.nhnacademy.twojopingbatch.coupon.repository.policy.CouponPolicyRepository;
import com.nhnacademy.twojopingbatch.coupon.service.BirthdayCouponService;
import com.nhnacademy.twojopingbatch.mybatis.mapper.MemberCouponMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;

/**
 * BirthdayCouponServiceImpl
 * 이 클래스는 매월 특정 월의 생일자들에게 쿠폰을 발급하는 서비스를 제공합니다.
 * 쿠폰 정책을 조회하고, 신규 쿠폰을 발급하거나 기존 쿠폰이 존재할 경우 해당 쿠폰을 재사용하여 생일 쿠폰을 발급합니다.
 *
 * @see BirthdayCouponService
 * @see CouponPolicy
 * @see CouponRepository
 * @see MemberCouponMapper
 * @since 1.0
 * @author Luha
 */
@Service
@RequiredArgsConstructor
public class BirthdayCouponServiceImpl implements BirthdayCouponService {

    private final CouponPolicyRepository couponPolicyRepository;
    private final CouponRepository couponRepository;
    private final MemberCouponMapper memberCouponMapper;

    /**
     * 월별 생일자에게 생일 쿠폰을 발급하는 메서드.
     * 현재 월의 생일자 목록을 조회하여 해당 쿠폰 정책에 따라 신규 쿠폰을 발급합니다.
     * 기존 쿠폰이 존재하지 않을 경우에만 신규 쿠폰이 생성되며, 기존 쿠폰이 있을 경우 해당 쿠폰을 사용합니다.
     * 각 회원에게 쿠폰을 매핑하여 데이터베이스에 저장합니다.
     */
    @Override
    @Transactional
    public long issueBirthdayCoupon() {

        LocalDate today = LocalDate.now();
        YearMonth currentYearMonth = YearMonth.from(today);
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();
        int month = today.getMonthValue();

        CouponPolicy birthdayPolicy = couponPolicyRepository.findByType(CouponPolicyType.BIRTHDAY)
                .orElseThrow(() -> new IllegalStateException("Birthday coupon policy not found"));


        String couponName = month + "월 생일 쿠폰";
        long couponId;
        boolean isCouponExist = couponRepository.existsByName(couponName);

        if(!isCouponExist) {

            Coupon initCoupon = new Coupon(null, couponName, today, lastDayOfMonth, null, birthdayPolicy);

            couponId = couponRepository.save(initCoupon).getId();

        } else {

            couponId = couponRepository.findIdByName(couponName);

        }
        return couponId;


    }
}
