package com.nhnacademy.twojopingbatch.batch.step;

import com.nhnacademy.twojopingbatch.batch.partitioner.CustomerPartitioner;
import com.nhnacademy.twojopingbatch.mybatis.dto.MemberCouponDto;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * DistributeCouponsStepConfig
 * 스프링 배치에서 쿠폰 분배 작업을 수행하는 Step 및 Partition Step을 정의하는 구성 클래스입니다.
 * 각 청크(Chunk)를 병렬로 처리하기 위해 Partitioning을 사용하며, 이를 통해 대규모 데이터를 효율적으로 처리합니다.
 * Step 구성 요소로는 ItemReader, ItemProcessor, ItemWriter가 포함되며,
 * 병렬 처리를 위해 TaskExecutor 및 Partition Step을 설정합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Configuration
public class DistributeCouponsStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ItemReader<Long> memberReader;
    private final ItemProcessor<Long, MemberCouponDto> couponProcessor;
    private final ItemWriter<MemberCouponDto> myBatisWriter;


    public DistributeCouponsStepConfig(JobRepository jobRepository,
                                       PlatformTransactionManager transactionManager,
                                       ItemReader<Long> memberReader,
                                       ItemProcessor<Long, MemberCouponDto> couponProcessor,
                                       ItemWriter<MemberCouponDto> myBatisWriter) {
        this.jobRepository = jobRepository;
        this.transactionManager = transactionManager;
        this.memberReader = memberReader;
        this.couponProcessor = couponProcessor;
        this.myBatisWriter = myBatisWriter;
    }

    /**
     * 기본 Step 정의
     * 회원 데이터를 읽고, 처리 후, DB에 저장하는 작업을 청크 단위로 처리합니다.
     *
     * @return Step 객체
     */
    @Bean
    public Step distributeCouponsStep() {
        return new StepBuilder("distributeCouponsStep", jobRepository)
                .<Long, MemberCouponDto>chunk(1000, transactionManager) // 청크 단위 처리
                .reader(memberReader)
                .processor(couponProcessor)
                .writer(myBatisWriter)
                .build();
    }

    /**
     * Partitioner 정의
     * 데이터를 병렬로 처리하기 위해 Partitioner를 설정합니다.
     *
     * @return CustomerPartitioner 인스턴스
     */
    @Bean
    public CustomerPartitioner customerPartitioner() {
        return new CustomerPartitioner();
    }

    /**
     * Partition Step 정의
     * 데이터 처리를 병렬로 수행하기 위해 Partition Step을 설정합니다.
     *
     * @param distributeCouponsStep 기본 Step
     * @param taskExecutor 병렬 작업을 수행할 TaskExecutor
     * @return Partitioned Step 객체
     */
    @Bean
    public Step partitionedDistributeCouponsStep(
            @Qualifier("distributeCouponsStep") Step distributeCouponsStep,
            @Qualifier("batchTaskExecutor") TaskExecutor taskExecutor) {
        return new StepBuilder("partitionedDistributeCouponsStep", jobRepository)
                .partitioner(distributeCouponsStep.getName(), customerPartitioner())
                .step(distributeCouponsStep)
                .taskExecutor(taskExecutor)
                .gridSize(10) // 병렬 처리 쓰레드 수
                .build();
    }

    /**
     * TaskExecutor 정의
     * 병렬 작업을 수행할 TaskExecutor를 설정합니다.
     *
     * @return TaskExecutor 객체
     */
    @Bean(name = "batchTaskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10); // 코어 스레드 수
        executor.setMaxPoolSize(40); // 최대 스레드 수
        executor.setQueueCapacity(200); // 작업 대기 큐 크기
        executor.setThreadNamePrefix("batch-thread-");
        executor.setAllowCoreThreadTimeOut(true);
        executor.setKeepAliveSeconds(60);
        executor.initialize();
        return executor;
    }
}