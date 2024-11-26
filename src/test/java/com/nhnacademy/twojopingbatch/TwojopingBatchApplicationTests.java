package com.nhnacademy.twojopingbatch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;

/**
 * TwojopingBatchApplicationTests
 *
 * 이 클래스는 TwojopingBatchApplication의 기본 테스트로,
 * 스프링 애플리케이션 컨텍스트가 정상적으로 로드되는지 확인합니다.
 *
 * @author Luha
 * @since 1.0
 */
@SpringBootTest
class TwojopingBatchApplicationTests {

    /**
     * 애플리케이션 컨텍스트 로드 테스트.
     *
     * Given: 애플리케이션이 구동됨.
     * When: 애플리케이션 컨텍스트가 로드됨.
     * Then: 컨텍스트가 예외 없이 정상적으로 로드되는지 검증.
     */



    @Test
    void contextLoads() {
        // 애플리케이션 컨텍스트가 정상적으로 로드되는지 확인합니다.

    }
    @Test
    void testMain() {
        // Given
        String[] args = new String[]{};

        // Mock SpringApplication.run

        try (var mockedStatic = mockStatic(SpringApplication.class)) {
            mockedStatic.when(() -> SpringApplication.run(TwojopingBatchApplication.class, args))
                    .thenReturn(null);

            // When
            TwojopingBatchApplication.main(args);

            // Then
            mockedStatic.verify(() -> SpringApplication.run(TwojopingBatchApplication.class, args));
        }
    }

}
