package com.nhnacademy.twojopingbatch.batch.service;

import com.nhnacademy.twojopingbatch.coupon.service.BirthdayCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/**
 * BatchSchedulerService
 *
 * 이 클래스는 스케줄링을 통해 매월 1일 자정 또는 매일 특정 시간에 생일 쿠폰 발급 작업을 수행하는 배치 스케줄러입니다.
 * 일정에 맞춰 BirthdayCouponService의 메서드를 호출하여 생일 쿠폰 발급을 자동화합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Service
@RequiredArgsConstructor
@Slf4j
@EnableRetry
public class BatchSchedulerService {

    private final BirthdayCouponService birthdayCouponService;

    /**
     * 스케줄러 메서드.
     *
     * 매월 1일 자정에 생일 쿠폰 발급을 실행하도록 설정되어 있으며,
     * 현재는 테스트 목적으로 매일 특정 시간에 실행되도록 주석 처리되어 있습니다.
     *
     * @Scheduled(cron = "0 0 0 1 * ?")  // 매월 1일 자정(00:00:00)에 실행
     *     예시 - 매일 13시 10분 (cron = "0 10 13 * * ?")
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    @Retryable(
            value = { Exception.class }, // 재시도할 예외 유형
            maxAttempts = 3,             // 최대 시도 횟수
            backoff = @Backoff(delay = 2000) // 재시도 간격 (밀리초 단위 2초)
    )
    @Transactional
    public void scheduleBirthdayCouponIssue() {

        long couponId = birthdayCouponService.issueBirthdayCoupon();
        log.info(LocalDate.now().getMonthValue() + "월 생일 쿠폰 발급이 성공적으로 완료되었습니다.");


    }

    @Recover
    public void recover(Exception e) {

        log.error(LocalDate.now().getMonthValue() + "월 생일 쿠폰 발급 중 오류가 발생했습니다: {}", e.getMessage(), e);

    }
}
