package com.ohgiraffers.springdatajpa.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.ohgiraffers.springdatajpa")
public class BeanConfig {

    @Bean
    public ModelMapper modelMapper() {
        /* comment.
        *   entity 클래스는 데이터의 무결성을 유지하기 위해
        *   setter 사용을 지양한다.
        *   그렇다면 dto 값을 어떻게 entity 에 set(넣을까)할까?
        *   modelmapper 는 dto <-> entity 간 변환을 용이하게 하는
        *   라이브러리 이다.
        *  */

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                // private 필드에 접근하기 위한 설정
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                // DTO 필드와 Entity 필드 매칭 가능 여부 설정
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }
}
