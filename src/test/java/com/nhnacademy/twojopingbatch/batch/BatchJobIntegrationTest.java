package com.nhnacademy.twojopingbatch.batch;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("dev") // dev 프로파일 사용
 class BatchJobIntegrationTest {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job distributeCouponsJob;

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private static final long TEST_COUPON_ID = 397; // 테스트용 쿠폰 ID
    private static final int JANUARY_MEMBER_COUNT = 3348; // 1월 생일자 수
    private static final int BIRTHDAY_MONTH = 11;

    @Test
     void testDistributeCouponsForJanuary() throws Exception {
        // JobParameters 설정
        JobParameters jobParameters = new JobParametersBuilder()
                .addString("month", String.valueOf(BIRTHDAY_MONTH)) // 1월 생일 달 전달
                .addLong("couponId", TEST_COUPON_ID) // 테스트 쿠폰 ID
                .addLong("time", System.currentTimeMillis()) // 고유 타임스탬프
                .toJobParameters();

        // 배치 작업 실행 전 시간 기록
        long startTime = System.currentTimeMillis();

        // 배치 작업 실행
        jobLauncher.run(distributeCouponsJob, jobParameters); // 반환값 사용하지 않음


        // 배치 작업 실행 후 시간 기록
        long endTime = System.currentTimeMillis();

        // 실행 시간 계산
        long executionTime = endTime - startTime;

        // 결과 검증
        long distributedCoupons = ((Number) entityManager.createNativeQuery(
                        "SELECT COUNT(*) FROM member_coupon WHERE coupon_id = :couponId"
                )
                .setParameter("couponId", TEST_COUPON_ID)
                .getSingleResult()).longValue();

        // 중복 데이터 없는지 확인
        long duplicateCoupons = ((Number) entityManager.createNativeQuery(
                        "SELECT COUNT(*) FROM (SELECT customer_id, COUNT(*) AS cnt FROM member_coupon " +
                                "WHERE coupon_id = :couponId GROUP BY customer_id HAVING cnt > 1) AS duplicates"
                )
                .setParameter("couponId", TEST_COUPON_ID)
                .getSingleResult()).longValue();

        // 실행 시간 출력
        System.out.println("Batch Execution Time: " + executionTime + "ms");
        System.out.println("Total Coupons Distributed: " + distributedCoupons);
        System.out.println("Duplicate Coupons: " + duplicateCoupons);

        assertEquals(0, duplicateCoupons, "There should be no duplicate coupons");
        assertEquals(JANUARY_MEMBER_COUNT, distributedCoupons,
                "Expected " + JANUARY_MEMBER_COUNT + " coupons, but got " + distributedCoupons);


    }
    @AfterEach
    public void cleanupTestData() {
        EntityManager entityFactoryManager = entityManagerFactory.createEntityManager();
        entityFactoryManager.getTransaction().begin();

        entityFactoryManager.createNativeQuery("DELETE FROM member_coupon WHERE coupon_id = :couponId")
                .setParameter("couponId", TEST_COUPON_ID)
                .executeUpdate();

        entityFactoryManager.getTransaction().commit();
        entityFactoryManager.close();
    }

}