package com.ohgiraffers.associationmapping.section03.bidirection;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BiRepository {

    @PersistenceContext
    private EntityManager manager;


    public Menu findMenu(int menuCode) {
        return manager.find(Menu.class, menuCode);
    }

    public Category findCategory(int categoryCode) {

        return manager.find(Category.class, categoryCode);
    }

    public void save(Menu newMenu) {

        manager.persist(newMenu);
    }

    public void saveCategory(Category category) {

        manager.persist(category);
    }
}
