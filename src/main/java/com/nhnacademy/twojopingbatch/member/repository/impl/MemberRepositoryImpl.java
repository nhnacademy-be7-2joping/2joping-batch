package com.nhnacademy.twojopingbatch.member.repository.impl;

import com.nhnacademy.twojopingbatch.member.repository.MemberQuerydslRepository;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.nhnacademy.twojopingbatch.member.entity.QMember.member;

/**
 * MemberRepositoryImpl
 *
 * 생일 월을 기준으로 특정 월에 생일이 있는 회원 ID 목록을 조회하는 기능을 제공하는
 * QueryDSL 기반의 커스텀 리포지토리 구현 클래스입니다.
 *
 * @author Luha
 * @since 1.0
 */
@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    /**
     * 주어진 월에 해당하는 생일을 가진 회원들의 ID를 조회하는 메서드.
     *
     * @param month 조회할 생일의 월
     * @return 주어진 월에 해당하는 회원 ID의 리스트
     */
    @Override
    public List<Long> findMemberIdsByBirthMonth(int month) {
        return queryFactory
                .select(member.customerId)
                .from(member)
                .where(Expressions.numberTemplate(Integer.class, "MONTH({0})", member.birthday).eq(month))
                .fetch();
    }
}
