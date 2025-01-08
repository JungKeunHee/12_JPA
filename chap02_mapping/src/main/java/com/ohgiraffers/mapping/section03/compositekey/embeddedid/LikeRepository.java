package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class LikeRepository {

    @PersistenceContext
    private EntityManager manager;

    public void save(Like like) {
        manager.persist(like);
    }
}
