package com.nhnacademy.twojopingbatch.coupon.service;

import com.nhnacademy.twojopingbatch.coupon.entity.Coupon;
import com.nhnacademy.twojopingbatch.coupon.entity.CouponPolicy;
import com.nhnacademy.twojopingbatch.coupon.enums.CouponPolicyType;
import com.nhnacademy.twojopingbatch.coupon.repository.coupon.CouponRepository;
import com.nhnacademy.twojopingbatch.coupon.repository.policy.CouponPolicyRepository;
import com.nhnacademy.twojopingbatch.coupon.service.impl.BirthdayCouponServiceImpl;
import com.nhnacademy.twojopingbatch.member.repository.MemberRepository;
import com.nhnacademy.twojopingbatch.mybatis.mapper.MemberCouponMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * BirthdayCouponServiceImplTest
 *
 * 이 클래스는 BirthdayCouponServiceImpl의 issueBirthdayCouponsForMonth 메서드를 테스트합니다.
 * 생일 쿠폰 정책에 따라 회원에게 쿠폰을 발급하는 과정을 검증합니다.
 *
 * @author Luha
 * @since 1.0
 */
public class BirthdayCouponServiceImplTest {
    @Mock
    private MemberRepository memberRepository;

    @Mock
    private CouponPolicyRepository couponPolicyRepository;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private MemberCouponMapper memberCouponMapper;

    @InjectMocks
    private BirthdayCouponServiceImpl birthdayCouponService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 생일 쿠폰 발행 메서드 테스트: 정책이 없을 때
     */
    @Test
    void issueBirthdayCouponsForMonth_policyNotFound() {
        // 쿠폰 정책이 없을 경우 예외가 발생하는지 확인
        when(couponPolicyRepository.findByType(CouponPolicyType.BIRTHDAY))
                .thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> birthdayCouponService.issueBirthdayCouponsForMonth());
    }

    /**
     * 생일 쿠폰 발행 메서드 테스트: 쿠폰 정책이 있을 때
     */
    @Test
    void issueBirthdayCouponsForMonth_couponExists() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        YearMonth currentYearMonth = YearMonth.from(today);
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();

        // 테스트용 쿠폰 정책 및 멤버 ID 리스트 설정
        CouponPolicy birthdayPolicy = new CouponPolicy();
        List<Long> memberList = Arrays.asList(1L, 2L, 3L);

        // 쿠폰 정책 및 멤버 ID 조회 설정
        when(couponPolicyRepository.findByType(CouponPolicyType.BIRTHDAY))
                .thenReturn(Optional.of(birthdayPolicy));
        when(memberRepository.findMemberIdsByBirthMonth(month)).thenReturn(memberList);

        // 쿠폰이 존재하지 않을 경우 새로 생성되도록 설정
        when(couponRepository.existsByName(any())).thenReturn(false);
        when(couponRepository.save(any(Coupon.class))).thenAnswer(invocation -> {
            Coupon savedCoupon = invocation.getArgument(0);
            return new Coupon(
                    1L, // ID를 직접 설정
                    savedCoupon.getName(),
                    savedCoupon.getCreatedAt(),
                    savedCoupon.getExpiredAt(),
                    savedCoupon.getQuantity(),
                    savedCoupon.getCouponPolicy()
            );
        });

        // 메서드 호출
        birthdayCouponService.issueBirthdayCouponsForMonth();

        // 쿠폰 발행 여부 및 저장된 데이터 확인
        verify(couponRepository, times(1)).save(any(Coupon.class));
        verify(memberCouponMapper, times(1)).insertMemberCoupons(anyList());
    }

    /**
     * 생일 쿠폰 발행 메서드 테스트: 기존 쿠폰이 있을 때
     */
    @Test
    void issueBirthdayCouponsForMonth_couponAlreadyExists() {
        LocalDate today = LocalDate.now();
        int month = today.getMonthValue();
        YearMonth currentYearMonth = YearMonth.from(today);
        LocalDate lastDayOfMonth = currentYearMonth.atEndOfMonth();

        CouponPolicy birthdayPolicy = new CouponPolicy();
        List<Long> memberList = Arrays.asList(1L, 2L, 3L);

        when(couponPolicyRepository.findByType(CouponPolicyType.BIRTHDAY))
                .thenReturn(Optional.of(birthdayPolicy));
        when(memberRepository.findMemberIdsByBirthMonth(month)).thenReturn(memberList);

        // 기존 쿠폰이 존재하는 경우
        when(couponRepository.existsByName(any())).thenReturn(true);
        when(couponRepository.findIdByName(any())).thenReturn(1L);

        birthdayCouponService.issueBirthdayCouponsForMonth();

        // 새로운 쿠폰을 저장하지 않고 바로 ID 조회 및 매퍼 호출만 실행
        verify(couponRepository, never()).save(any(Coupon.class));
        verify(memberCouponMapper, times(1)).insertMemberCoupons(anyList());
    }
}
