package com.nhnacademy.twojopingbatch.batch.processor;

import com.nhnacademy.twojopingbatch.mybatis.dto.MemberCouponDto;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.UUID;

@Component
public class CouponProcessor implements ItemProcessor<Long, MemberCouponDto> {


    @Override
    public MemberCouponDto process(Long item) throws Exception {
        return null;
    }
}