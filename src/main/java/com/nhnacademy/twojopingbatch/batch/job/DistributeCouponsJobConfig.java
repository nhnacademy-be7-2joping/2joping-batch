package com.nhnacademy.twojopingbatch.batch.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DistributeCouponsJobConfig
 * 스프링 배치 작업(Job)을 구성하는 설정 클래스입니다.
 * 이 클래스는 쿠폰을 분배하는 배치 작업(distributeCouponsJob)을 설정하며,
 * 작업의 시작 스텝으로 distributeCouponsStep을 사용합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Configuration
public class DistributeCouponsJobConfig {

    private final JobRepository jobRepository;
    private final Step distributeCouponsStep;


    public DistributeCouponsJobConfig(JobRepository jobRepository, Step distributeCouponsStep) {
        this.jobRepository = jobRepository;
        this.distributeCouponsStep = distributeCouponsStep;
    }

    /**
     * distributeCouponsJob
     * 쿠폰 분배 작업을 정의합니다.
     * 작업은 distributeCouponsStep을 시작으로 실행됩니다.
     *
     * @return 정의된 Job 객체
     */
    @Bean
    public Job distributeCouponsJob() {
        return new JobBuilder("distributeCouponsJob", jobRepository)
                .start(distributeCouponsStep)
                .build();
    }
}