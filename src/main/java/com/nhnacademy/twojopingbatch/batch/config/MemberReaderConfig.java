package com.nhnacademy.twojopingbatch.batch.config;




import com.nhnacademy.twojopingbatch.member.entity.QMember;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class MemberReaderConfig {

    private final JPAQueryFactory queryFactory;


    @Bean
    @StepScope
    public ItemReader<Long> querydslReader(JPAQueryFactory queryFactory, @Value("#{jobParameters['month']}") int month) {
        return new ItemReader<Long>() {
            private final List<Long> memberIds = queryFactory.select(QMember.member.customerId)
                    .from(QMember.member)
                    .where(Expressions.numberTemplate(Integer.class, "MONTH({0})", QMember.member.birthday).eq(month))
                    .fetch();
            private int currentIndex = 0;

            @Override
            public Long read() {
                return currentIndex < memberIds.size() ? memberIds.get(currentIndex++) : null;
            }
        };
    }
}
