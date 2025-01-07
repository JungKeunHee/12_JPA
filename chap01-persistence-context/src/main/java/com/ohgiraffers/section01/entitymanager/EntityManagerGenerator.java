package com.ohgiraffers.section01.entitymanager;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class EntityManagerGenerator {

    public static EntityManager getInstance() {
        // 아까 만든 공장 인스턴스 생성
        EntityManagerFactory factory = EntityManagerFactoryGenerator.getInstance();

        // 공장에 대한 매니저를 만드는 메소드
        return factory.createEntityManager();
    }
}
