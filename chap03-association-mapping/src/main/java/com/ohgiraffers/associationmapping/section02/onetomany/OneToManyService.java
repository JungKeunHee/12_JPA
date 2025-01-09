package com.ohgiraffers.associationmapping.section02.onetomany;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OneToManyService {

    @Autowired
    private OneToManyRepository repository;

    @Transactional(readOnly = true)
    public Category findCategory(int categoryCode) {

        Category category = repository.find(categoryCode);

        System.out.println("category = " + category);

        return category;
    }
}
