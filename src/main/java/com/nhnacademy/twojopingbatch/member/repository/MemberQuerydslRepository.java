package com.nhnacademy.twojopingbatch.member.repository;

import java.util.List;

/**
 * MemberQuerydslRepository
 *
 * QueryDSL을 사용하여 특정 조건에 맞는 회원 정보를 조회하는 메서드를 제공하는 리포지토리 인터페이스입니다.
 * 생일 월을 기준으로 회원 ID 목록을 조회하는 메서드를 정의합니다.
 *
 * @since 1.0
 * @author Luha
 */
public interface MemberQuerydslRepository {

    /**
     * 주어진 월에 생일이 있는 회원들의 ID를 조회하는 메서드.
     *
     * @param month 조회할 생일의 월
     * @return 주어진 월에 해당하는 회원 ID의 리스트
     */
    List<Long> findMemberIdsByBirthMonth(int month);

}
