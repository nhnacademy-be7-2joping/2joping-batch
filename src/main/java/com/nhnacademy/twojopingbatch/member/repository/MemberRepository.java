package com.nhnacademy.twojopingbatch.member.repository;


import com.nhnacademy.twojopingbatch.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MemberRepository
 *
 * 회원 엔티티에 대한 CRUD 작업을 제공하는 JPA 리포지토리 인터페이스입니다.
 * 또한, QueryDSL을 사용한 맞춤형 조회 기능을 포함하여 회원의 특정 조건 조회 메서드를 제공합니다.
 *
 * @see JpaRepository
 * @see MemberQuerydslRepository
 * @since 1.0
 * @author Luha
 */
public interface MemberRepository  extends JpaRepository<Member, Long>, MemberQuerydslRepository {
}
