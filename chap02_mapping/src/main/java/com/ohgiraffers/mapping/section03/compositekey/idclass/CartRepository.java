package com.ohgiraffers.mapping.section03.compositekey.idclass;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class CartRepository {

    @PersistenceContext
    private EntityManager manager;

    public void save(Cart newCart) {
        manager.persist(newCart);
    }
}
