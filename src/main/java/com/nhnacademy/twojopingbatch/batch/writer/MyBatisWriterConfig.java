package com.nhnacademy.twojopingbatch.batch.writer;

import com.nhnacademy.twojopingbatch.mybatis.dto.MemberCouponDto;
import com.nhnacademy.twojopingbatch.mybatis.mapper.MemberCouponMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatisWriterConfig
 * 스프링 배치에서 MyBatis를 활용하여 데이터베이스에 데이터를 쓰는 Writer를 정의하는 구성 클래스입니다.
 * MemberCouponDto 객체를 데이터베이스에 일괄 삽입하기 위한 ItemWriter를 생성합니다.
 * MyBatis의 Mapper를 사용하여 효율적으로 대량의 데이터를 처리할 수 있도록 설정됩니다.
 *
 * @author Luha
 * @since 1.0
 */
@Configuration
public class MyBatisWriterConfig {

    /**
     * MyBatis 기반 ItemWriter 정의
     * 청크 단위로 처리된 MemberCouponDto 리스트를 MyBatis를 통해 데이터베이스에 삽입합니다.
     * 청크에서 전달받은 데이터를 새로운 리스트로 변환하여 MyBatis Mapper의 bulkInsertMemberCoupons 메서드로 전달합니다.
     * 이를 통해 대량의 데이터를 효율적으로 저장할 수 있습니다
     *
     * @param memberCouponMapper MyBatis Mapper 인터페이스로, 데이터베이스 삽입 작업을 처리
     * @return MyBatis 기반의 ItemWriter
     */
    @Bean
    public ItemWriter<MemberCouponDto> myBatisWriter(MemberCouponMapper memberCouponMapper) {
        return chunk -> {
            // 새로운 리스트로 변환
            List<MemberCouponDto> items = new ArrayList<>(chunk.getItems());
            memberCouponMapper.bulkInsertMemberCoupons(items);
        };
    }
}