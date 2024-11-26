package com.nhnacademy.twojopingbatch.batch.scheduler;

import com.nhnacademy.twojopingbatch.coupon.service.BirthdayCouponService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * BatchScheduler
 * 스프링 배치 스케줄러 클래스입니다.
 * 매월 1일 00시 00분에 실행되며, 쿠폰 발급과 관련된 배치 작업을 실행합니다.
 * Spring Batch의 JobLauncher와 Job을 사용하여 배치 작업을 실행하며,
 * BirthdayCouponService를 통해 필요한 쿠폰 ID를 가져옵니다.
 *
 * @author Luha
 * @since 1.0
 */
@Configuration
@EnableScheduling
@Slf4j
@RequiredArgsConstructor
public class BatchScheduler {

    private final JobLauncher jobLauncher;
    private final Job distributeCouponsJob;
    private final BirthdayCouponService birthdayCouponService;

    /**
     * 매월 1일 00시 00분에 실행되는 배치 작업 메서드.
     * BirthdayCouponService를 호출하여 쿠폰 ID를 생성한 뒤,
     * 배치 작업에 필요한 JobParameters를 구성하여 JobLauncher를 통해 실행합니다.
     * 배치 작업이 이미 실행 중인 경우, JobExecutionAlreadyRunningException을 처리하며,
     * 예기치 못한 에러 발생 시 로그를 남깁니다.
     */
    @Scheduled(cron = "0 0 0 1 * ?") // 매월 1일 00시 00분 실행
    public void scheduleDistributeCouponsJob() {
        try {
            Long couponId = birthdayCouponService.issueBirthdayCoupon();

            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("month", String.valueOf(LocalDateTime.now().getMonthValue())) // month 전달
                    .addLong("couponId", couponId) // 쿠폰 ID 전달
                    .addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(distributeCouponsJob, jobParameters);

        } catch (JobExecutionAlreadyRunningException e) {

            log.warn("Job is already running: {}", e.getMessage(), e);

        } catch (Exception e) {

            log.error("An unexpected error occurred while running the job: {}", e.getMessage(), e);

        }
    }
}