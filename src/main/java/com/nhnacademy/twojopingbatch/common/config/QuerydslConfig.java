package com.nhnacademy.twojopingbatch.common.config;


import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * QuerydslConfig
 * 이 클래스는 QueryDSL 설정을 관리하는 구성 클래스입니다.
 * JPAQueryFactory 빈을 생성하여, QueryDSL을 통해 데이터베이스 질의를 수행할 수 있도록 지원합니다.
 *
 * @author Luha
 * @since 1.0
 */
@Configuration
public class QuerydslConfig {

    /**
     * JPAQueryFactory 빈을 생성하여 QueryDSL의 JPA 쿼리 기능을 제공하는 메서드.
     *
     * @param entityManager JPA의 EntityManager 인스턴스를 주입받아 사용
     * @return JPAQueryFactory 인스턴스
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}