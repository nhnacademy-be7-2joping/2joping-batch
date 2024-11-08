package com.nhnacademy.twojopingbatch.batch;

import com.nhnacademy.twojopingbatch.batch.service.BatchSchedulerService;
import com.nhnacademy.twojopingbatch.coupon.service.BirthdayCouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;

/**
 * BatchSchedulerServiceTest
 *
 * 이 클래스는 BatchSchedulerService의 scheduleBirthdayCouponIssue 메서드를 테스트합니다.
 * 스케줄러가 올바르게 BirthdayCouponService의 issueBirthdayCouponsForMonth 메서드를 호출하는지 확인합니다.
 *
 * @author Luha
 * @since 1.0
 */
class BatchSchedulerServiceTest {

    @Mock
    private BirthdayCouponService birthdayCouponService;

    @InjectMocks
    private BatchSchedulerService batchSchedulerService;

    private static final Logger log = LoggerFactory.getLogger(BatchSchedulerServiceTest.class);

    /**
     * 초기 설정.
     * Mockito 초기화.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * scheduleBirthdayCouponIssue 메서드 테스트.
     *
     * Given: BirthdayCouponService의 모의 객체가 생성됨.
     * When: scheduleBirthdayCouponIssue 메서드를 호출.
     * Then: BirthdayCouponService의 issueBirthdayCouponsForMonth 메서드가 호출되었는지 검증.
     */
    @Test
    void testScheduleBirthdayCouponIssue() {
        // 메서드 호출
        batchSchedulerService.scheduleBirthdayCouponIssue();

        // 메서드 호출 검증
        verify(birthdayCouponService).issueBirthdayCouponsForMonth();
    }


    /**
     * scheduleBirthdayCouponIssue 메서드 정상 호출 테스트.
     *
     * Given: BirthdayCouponService의 모의 객체가 생성됨.
     * When: scheduleBirthdayCouponIssue 메서드를 호출.
     * Then: BirthdayCouponService의 issueBirthdayCouponsForMonth 메서드가 호출되었는지 검증.
     */
    @Test
    void testScheduleBirthdayCouponIssueSuccess() {
        batchSchedulerService.scheduleBirthdayCouponIssue();
        verify(birthdayCouponService, times(1)).issueBirthdayCouponsForMonth();
    }

    /**
     * scheduleBirthdayCouponIssue 메서드에서 예외 발생 시 로그 기록 테스트.
     *
     * Given: BirthdayCouponService의 issueBirthdayCouponsForMonth 메서드가 예외를 던짐.
     * When: scheduleBirthdayCouponIssue 메서드를 호출.
     * Then: 로그에 오류 메시지가 기록되었는지 확인.
     */
    @Test
    void testScheduleBirthdayCouponIssueExceptionLogging() {
        doThrow(new RuntimeException("Test exception")).when(birthdayCouponService).issueBirthdayCouponsForMonth();

        try {
            batchSchedulerService.scheduleBirthdayCouponIssue();
        } catch (Exception e) {
            log.error("예외가 발생하여 스케줄러 작업이 실패했습니다: {}", e.getMessage(), e);
        }

        verify(birthdayCouponService, times(1)).issueBirthdayCouponsForMonth();
    }
}