package com.nhnacademy.twojopingbatch.batch.reader;




import com.nhnacademy.twojopingbatch.coupon.entity.QMemberCoupon;
import com.nhnacademy.twojopingbatch.member.entity.QMember;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * MemberReaderConfig
 * 스프링 배치에서 사용되는 Reader 구성 클래스입니다.
 * 데이터베이스에서 회원 데이터를 페이징 방식으로 읽어오는 Reader를 생성합니다.
 * 이 클래스는 특정 조건(월, ID 범위, 쿠폰 ID)에 따라 회원 데이터를 조회하며,
 * 중복 데이터를 처리하지 않도록 설정됩니다.
 *
 * @author Luha
 * @since 1.0
 */
@Configuration
public class MemberReaderConfig {

    private final JPAQueryFactory queryFactory;



    public MemberReaderConfig(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    /**
     * MemberReader Bean
     * 회원 데이터를 읽어오는 AbstractItemCountingItemStreamItemReader를 생성합니다.
     * 특정 ID 범위, 생일 월(month), 쿠폰 ID를 조건으로 데이터베이스에서 회원 ID 목록을 읽습니다.
     *
     * @param startId 파티션의 시작 ID
     * @param endId 파티션의 끝 ID
     * @param month 회원의 생일 월
     * @param couponId 처리할 쿠폰 ID
     * @return 회원 데이터를 읽어오는 ItemStreamReader
     */
    @Bean
    @StepScope
    public AbstractItemCountingItemStreamItemReader<Long> memberReader(
            @Value("#{stepExecutionContext['startId']}") Long startId, // 파티션 시작 ID
            @Value("#{stepExecutionContext['endId']}") Long endId,     // 파티션 끝 ID
            @Value("#{jobParameters['month']}") Integer month,
            @Value("#{jobParameters['couponId']}") Long couponId) {

        AbstractItemCountingItemStreamItemReader<Long> reader = new AbstractItemCountingItemStreamItemReader<>() {

            private List<Long> memberIds = null;
            private int currentIndex = 0;
            private Long lastId = null; // 마지막 ID 저장

            @Override
            protected void doOpen() {
                // 리더 열 때 초기화
                currentIndex = 0;
                lastId = null;
                memberIds = null;

            }

            @Override
            protected void doClose() {
                // 리더 닫을 때 정리 작업
                memberIds = null;
                currentIndex = 0;
                lastId = null;
            }

            @Override
            protected Long doRead() {
                if (memberIds == null || currentIndex >= memberIds.size()) {
                    QMember member = QMember.member;
                    QMemberCoupon memberCoupon = QMemberCoupon.memberCoupon;

                    int pageSize = 3000;

                    // 쿼리 실행하여 페이징 데이터 로드
                    memberIds = queryFactory.select(member.id)
                            .from(member)
                            .where(member.birthdayMonth.eq(month)
                                    .and((startId != null && endId != null) ? member.id.between(startId, endId) : null) // startId, endId 조건 추가
                                    .and(lastId != null ? member.id.gt(lastId) : null)
                                    .and(member.id.notIn(
                                            JPAExpressions.select(memberCoupon.member.id)
                                                    .from(memberCoupon)
                                                    .where(memberCoupon.coupon.id.eq(couponId))
                                    )))
                            .orderBy(member.id.asc())
                            .limit(pageSize)
                            .fetch();

                    if (memberIds.isEmpty()) {
                        return null; // 더 이상 데이터가 없으면 null 반환
                    }

                    lastId = memberIds.getLast();
                    currentIndex = 0; // 인덱스 초기화
                }

                return memberIds.get(currentIndex++);
            }
        };
        reader.setName("memberReader");
        return reader;
    }
}
