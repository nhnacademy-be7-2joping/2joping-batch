package com.nhnacademy.twojopingbatch.batch;


import com.nhnacademy.twojopingbatch.batch.scheduler.BatchScheduler;
import com.nhnacademy.twojopingbatch.coupon.service.BirthdayCouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BatchSchedulerTest {

    private JobLauncher jobLauncher;
    private Job distributeCouponsJob;
    private BirthdayCouponService birthdayCouponService;
    private BatchScheduler batchScheduler;

    @BeforeEach
    void setUp() {
        // Mock 의존성 생성
        jobLauncher = mock(JobLauncher.class);
        distributeCouponsJob = mock(Job.class);
        birthdayCouponService = mock(BirthdayCouponService.class);

        // BatchScheduler 초기화
        batchScheduler = new BatchScheduler(jobLauncher, distributeCouponsJob, birthdayCouponService);
    }

    @Test
    void testScheduleDistributeCouponsJob_success() throws Exception {
        // Mock 동작 정의
        Long mockCouponId = 123L;
        when(birthdayCouponService.issueBirthdayCoupon()).thenReturn(mockCouponId);

        // 스케줄 실행
        batchScheduler.scheduleDistributeCouponsJob();

        // 호출 검증
        verify(birthdayCouponService, times(1)).issueBirthdayCoupon();
        verify(jobLauncher, times(1)).run(eq(distributeCouponsJob), any(JobParameters.class));
    }

    @Test
    void testScheduleDistributeCouponsJob_jobAlreadyRunningException() throws Exception {
        // Mock 동작 정의
        when(birthdayCouponService.issueBirthdayCoupon()).thenReturn(123L);
        doThrow(new RuntimeException("Job is already running")).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // 스케줄 실행
        batchScheduler.scheduleDistributeCouponsJob();

        // 호출 검증
        verify(birthdayCouponService, times(1)).issueBirthdayCoupon();
        verify(jobLauncher, times(1)).run(eq(distributeCouponsJob), any(JobParameters.class));
    }

    @Test
    void testScheduleDistributeCouponsJob_unexpectedException() throws Exception {
        // Mock 동작 정의
        when(birthdayCouponService.issueBirthdayCoupon()).thenThrow(new RuntimeException("Unexpected error"));

        // 스케줄 실행
        batchScheduler.scheduleDistributeCouponsJob();

        // 호출 검증
        verify(birthdayCouponService, times(1)).issueBirthdayCoupon();
        verify(jobLauncher, times(0)).run(any(Job.class), any(JobParameters.class));
    }

    @Test
    void testJobExecutionAlreadyRunningExceptionHandling() throws Exception {
        // Mock 동작 정의
        when(birthdayCouponService.issueBirthdayCoupon()).thenReturn(123L);
        doThrow(new JobExecutionAlreadyRunningException("Job is already running"))
                .when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // 스케줄 실행
        batchScheduler.scheduleDistributeCouponsJob();

        // 호출 검증
        verify(birthdayCouponService, times(1)).issueBirthdayCoupon();
        verify(jobLauncher, times(1)).run(any(Job.class), any(JobParameters.class));
        // 예외 발생 후에도 프로그램이 중단되지 않았는지 확인
    }


}