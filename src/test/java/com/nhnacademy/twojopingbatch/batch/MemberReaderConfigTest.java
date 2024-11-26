package com.nhnacademy.twojopingbatch.batch;


import com.nhnacademy.twojopingbatch.batch.reader.MemberReaderConfig;
import com.nhnacademy.twojopingbatch.member.entity.QMember;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberReaderConfigTest {

    private JPAQueryFactory queryFactory;
    private MemberReaderConfig memberReaderConfig;

    @BeforeEach
    void setUp() {
        queryFactory = mock(JPAQueryFactory.class);
        memberReaderConfig = new MemberReaderConfig(queryFactory);
    }

    @Test
    void testReaderWithAllConditions() throws Exception {
        // Given
        Long startId = 1L;
        Long endId = 100L;
        Integer month = 11;
        Long couponId = 123L;

        // Mock 데이터 설정
        List<Long> mockMemberIds = List.of(2L, 3L, 4L);

        // Mock QueryDSL 동작 설정
        JPAQuery<Long> mockQuery = mock(JPAQuery.class);
        when(mockQuery.fetch()).thenReturn(mockMemberIds); // 데이터를 한 번만 반환
        when(mockQuery.orderBy(any(OrderSpecifier.class))).thenReturn(mockQuery);
        when(mockQuery.limit(anyLong())).thenReturn(mockQuery);
        when(mockQuery.where(any(Predicate.class))).thenReturn(mockQuery);
        when(mockQuery.from(QMember.member)).thenReturn(mockQuery);

        when(queryFactory.select(QMember.member.id)).thenReturn(mockQuery);

        // Reader 생성
        AbstractItemCountingItemStreamItemReader<Long> reader = memberReaderConfig.memberReader(startId, endId, month, couponId);

        // Reader 열기
        ExecutionContext context = new ExecutionContext();
        reader.open(context);

        // When & Then: Mock 데이터를 반복적으로 읽음
        assertEquals(2L, reader.read());
        assertEquals(3L, reader.read());
        assertEquals(4L, reader.read());


        // Reader 닫기
        reader.close();
    }
    @Test
    void testReaderReturnsNullWhenNoDataFetched() throws Exception {
        // Given
        Long startId = 1L;
        Long endId = 100L;
        Integer month = 11;
        Long couponId = 123L;

        // Mock 데이터 설정: 빈 리스트를 반환
        List<Long> emptyMemberIds = List.of();

        // Mock QueryDSL 동작 설정
        JPAQuery<Long> mockQuery = mock(JPAQuery.class);
        when(mockQuery.fetch()).thenReturn(emptyMemberIds); // 빈 리스트 반환
        when(mockQuery.orderBy(any(OrderSpecifier.class))).thenReturn(mockQuery);
        when(mockQuery.limit(anyLong())).thenReturn(mockQuery);
        when(mockQuery.where(any(Predicate.class))).thenReturn(mockQuery);
        when(mockQuery.from(QMember.member)).thenReturn(mockQuery);

        when(queryFactory.select(QMember.member.id)).thenReturn(mockQuery);

        // Reader 생성
        AbstractItemCountingItemStreamItemReader<Long> reader = memberReaderConfig.memberReader(startId, endId, month, couponId);

        // Reader 열기
        ExecutionContext context = new ExecutionContext();
        reader.open(context);

        // When: 데이터가 비어 있는 경우
        Long result = reader.read();

        // Then: null 반환 확인
        assertNull(result);

        // Reader 닫기
        reader.close();
    }
    @Test
    void testReaderWithoutStartAndEndIdCondition() throws Exception {
        // Given
        Long startId = null; // 시작 ID 미설정
        Long endId = null; // 끝 ID 미설정
        Integer month = 11;
        Long couponId = 123L;

        // Mock 데이터 설정
        List<Long> mockMemberIds = List.of(2L, 3L, 4L);

        // Mock QueryDSL 동작 설정
        JPAQuery<Long> mockQuery = mock(JPAQuery.class);
        when(mockQuery.fetch()).thenReturn(mockMemberIds);
        when(mockQuery.orderBy(any(OrderSpecifier.class))).thenReturn(mockQuery);
        when(mockQuery.limit(anyLong())).thenReturn(mockQuery);

        // `where` 조건 테스트
        when(mockQuery.where(any(Predicate.class))).thenAnswer(invocation -> {
            Predicate predicate = invocation.getArgument(0);
            System.out.println("Generated Predicate: " + predicate); // 디버깅용 출력
            return mockQuery;
        });

        when(mockQuery.from(QMember.member)).thenReturn(mockQuery);

        when(queryFactory.select(QMember.member.id)).thenReturn(mockQuery);

        // Reader 생성
        AbstractItemCountingItemStreamItemReader<Long> reader = memberReaderConfig.memberReader(startId, endId, month, couponId);

        // Reader 열기
        ExecutionContext context = new ExecutionContext();
        reader.open(context);

        // When & Then: Mock 데이터를 반복적으로 읽음
        assertEquals(2L, reader.read());
        assertEquals(3L, reader.read());
        assertEquals(4L, reader.read());

        // Reader 닫기
        reader.close();
    }
    @Test
    void testReaderWithStartAndEndIdCondition() throws Exception {
        // Given
        Long startId = 50L; // 시작 ID 설정
        Long endId = 100L; // 끝 ID 설정
        Integer month = 11;
        Long couponId = 123L;

        // Mock 데이터 설정
        List<Long> mockMemberIds = List.of(51L, 75L, 99L);

        // Mock QueryDSL 동작 설정
        JPAQuery<Long> mockQuery = mock(JPAQuery.class);
        when(mockQuery.fetch()).thenReturn(mockMemberIds);
        when(mockQuery.orderBy(any(OrderSpecifier.class))).thenReturn(mockQuery);
        when(mockQuery.limit(anyLong())).thenReturn(mockQuery);

        // `where` 조건 테스트
        when(mockQuery.where(any(Predicate.class))).thenAnswer(invocation -> {
            Predicate predicate = invocation.getArgument(0);
            System.out.println("Generated Predicate: " + predicate); // 디버깅용 출력
            return mockQuery;
        });

        when(mockQuery.from(QMember.member)).thenReturn(mockQuery);

        when(queryFactory.select(QMember.member.id)).thenReturn(mockQuery);

        // Reader 생성
        AbstractItemCountingItemStreamItemReader<Long> reader = memberReaderConfig.memberReader(startId, endId, month, couponId);

        // Reader 열기
        ExecutionContext context = new ExecutionContext();
        reader.open(context);

        // When & Then: Mock 데이터를 반복적으로 읽음
        assertEquals(51L, reader.read());
        assertEquals(75L, reader.read());
        assertEquals(99L, reader.read());

        // Reader 닫기
        reader.close();
    }
    @Test
    void testReaderWithOnlyStartIdNull() throws Exception {
        // Given
        Long endId = 100L;
        Integer month = 11;
        Long couponId = 123L;

        // Mock 데이터 설정
        List<Long> mockMemberIds = List.of(2L, 3L, 4L);

        // Mock QueryDSL 동작 설정
        JPAQuery<Long> mockQuery = mock(JPAQuery.class);
        when(mockQuery.fetch()).thenReturn(mockMemberIds);
        when(mockQuery.orderBy(any(OrderSpecifier.class))).thenReturn(mockQuery);
        when(mockQuery.limit(anyLong())).thenReturn(mockQuery);

        // `where` 조건 테스트
        when(mockQuery.where(any(Predicate.class))).thenAnswer(invocation -> {
            Predicate predicate = invocation.getArgument(0);
            System.out.println("Generated Predicate (Only startId null): " + predicate);
            return mockQuery;
        });

        when(mockQuery.from(QMember.member)).thenReturn(mockQuery);

        when(queryFactory.select(QMember.member.id)).thenReturn(mockQuery);

        // Reader 생성
        AbstractItemCountingItemStreamItemReader<Long> reader = memberReaderConfig.memberReader(null, endId, month, couponId);

        // Reader 열기
        ExecutionContext context = new ExecutionContext();
        reader.open(context);

        // When & Then
        assertEquals(2L, reader.read());
        assertEquals(3L, reader.read());
        assertEquals(4L, reader.read());

        reader.close();
    }

    @Test
    void testReaderWithOnlyEndIdNull() throws Exception {
        // Given
        Long startId = 50L;
        Integer month = 11;
        Long couponId = 123L;

        // Mock 데이터 설정
        List<Long> mockMemberIds = List.of(2L, 3L, 4L);

        // Mock QueryDSL 동작 설정
        JPAQuery<Long> mockQuery = mock(JPAQuery.class);
        when(mockQuery.fetch()).thenReturn(mockMemberIds);
        when(mockQuery.orderBy(any(OrderSpecifier.class))).thenReturn(mockQuery);
        when(mockQuery.limit(anyLong())).thenReturn(mockQuery);

        // `where` 조건 테스트
        when(mockQuery.where(any(Predicate.class))).thenAnswer(invocation -> {
            Predicate predicate = invocation.getArgument(0);
            System.out.println("Generated Predicate (Only endId null): " + predicate);
            return mockQuery;
        });

        when(mockQuery.from(QMember.member)).thenReturn(mockQuery);

        when(queryFactory.select(QMember.member.id)).thenReturn(mockQuery);

        // Reader 생성
        AbstractItemCountingItemStreamItemReader<Long> reader = memberReaderConfig.memberReader(startId, null, month, couponId);

        // Reader 열기
        ExecutionContext context = new ExecutionContext();
        reader.open(context);

        // When & Then
        assertEquals(2L, reader.read());
        assertEquals(3L, reader.read());
        assertEquals(4L, reader.read());

        reader.close();
    }
}