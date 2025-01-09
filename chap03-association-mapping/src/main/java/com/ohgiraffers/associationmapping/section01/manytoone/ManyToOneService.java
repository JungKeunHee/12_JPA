package com.ohgiraffers.associationmapping.section01.manytoone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ManyToOneService {

    @Autowired
    private ManyToOneRepository manyToOneRepository;

    public Menu findMenu(int menuCode) {

        return manyToOneRepository.find(menuCode);
    }

    public String findCategoryName(int menuCode) {

        return manyToOneRepository.findCategoryName(menuCode);
    }

    @Transactional
    public void registMenu(MenuDTO newMenu) {

        Menu menu = new Menu(
                newMenu.getMenuCode(),
                newMenu.getMenuName(),
                newMenu.getMenuPrice(),
                new Category(
                        newMenu.getCategory().getCategoryCode(),
                        newMenu.getCategory().getCategoryName(),
                        newMenu.getCategory().getRefCategoryCode()
                ),
                newMenu.getOrderableStatus()
        );

        manyToOneRepository.regist(menu);

    }
}
