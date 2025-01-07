package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class EntityManagerFactoryGenerator {

    private static EntityManagerFactory factory
            = Persistence.createEntityManagerFactory("jpatest");

    // 외부에서 인스턴스 생성 못 하게 private 로 접근 제한
    private EntityManagerFactoryGenerator() {}

    // static 인스턴스를 리턴해주는 메소드 생성
    public static EntityManagerFactory getInstance() {
        return factory;
    }
}
