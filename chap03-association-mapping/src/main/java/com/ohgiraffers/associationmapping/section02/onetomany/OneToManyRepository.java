package com.ohgiraffers.associationmapping.section02.onetomany;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class OneToManyRepository {

    @PersistenceContext
    private EntityManager manager;

    public Category find(int categoryCode) {
        return manager.find(Category.class, categoryCode);
    }
}
