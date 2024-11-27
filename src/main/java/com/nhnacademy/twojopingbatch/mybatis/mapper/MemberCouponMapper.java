package com.nhnacademy.twojopingbatch.mybatis.mapper;

import com.nhnacademy.twojopingbatch.mybatis.dto.MemberCouponDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MemberCouponMapper
 * 회원 쿠폰 발급 정보를 관리하는 MyBatis 매퍼 인터페이스입니다.
 * 다수의 회원 쿠폰 발급 데이터를 대량으로 삽입할 수 있는 메서드를 제공합니다.
 *
 * @since 1.0
 * @author Luha
 */
@Mapper
public interface MemberCouponMapper {

    /**
     * 회원 쿠폰 발급 데이터를 대량 삽입하는 메서드.
     *
     * @param memberCouponDtos 삽입할 회원 쿠폰 발급 정보 리스트
     */
    void bulkInsertMemberCoupons(@Param("list") List<MemberCouponDto> memberCouponDtos);

}
