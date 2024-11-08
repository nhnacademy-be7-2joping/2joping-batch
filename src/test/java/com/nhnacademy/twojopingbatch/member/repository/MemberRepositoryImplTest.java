package com.nhnacademy.twojopingbatch.member.repository;


import com.nhnacademy.twojopingbatch.member.entity.Member;
import com.nhnacademy.twojopingbatch.member.entity.MemberTier;
import com.nhnacademy.twojopingbatch.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 * MemberRepositoryImplTest
 *
 * 이 클래스는 MemberRepositoryImpl의 findMemberIdsByBirthMonth 메서드를 테스트합니다.
 * 해당 월에 생일이 있는 회원의 ID 목록을 조회하는 기능을 검증합니다.
 *
 * @author Luha
 * @since 1.0
 */
@SpringBootTest
class MemberRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * 테스트 데이터 초기화.
     */
    @BeforeEach
    @Transactional
    void setUp() {

    }

    /**
     * findMemberIdsByBirthMonth 메서드 테스트.
     *
     * Given: 해당 월에 생일이 있는 회원들이 데이터베이스에 존재할 때.
     * When: 해당 월을 인자로 메서드를 호출할 때.
     * Then: 해당 월에 생일인 회원들의 ID가 반환되는지 검증.
     */
    @Test
    @Transactional
    void testFindMemberIdsByBirthMonth() {
        int month = 11;

        // When: 10월에 생일이 있는 회원 ID 조회
        List<Long> memberIds = memberRepository.findMemberIdsByBirthMonth(month);

        // Then: 결과 검증 (10월에 생일이 있는 회원 2명 확인)
        assertEquals(7, memberIds.size());
        assertTrue(memberIds.contains(41L));
        assertTrue(memberIds.contains(42L));
    }
}
