package com.nhnacademy.twojopingbatch;

import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@ConfigurationPropertiesScan
@MapperScan(basePackages = "com.nhnacademy.twojopingbatch.mybatis.mapper", factoryBean = MapperFactoryBean.class)
public class TwojopingBatchApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwojopingBatchApplication.class, args);
    }

}
