package com.ohgiraffers.jpatest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaService {

    @Autowired
    private MenuRepository menuRepository;

    public MenuDTO findByMenu(int menuCode) {

        Menu menu = menuRepository.findByMenuCode(menuCode);

            return new MenuDTO(
                    menu.getMenuCode(),
                    menu.getMenuName(),
                    menu.getMenuPrice(),
                    menu.getCategoryCode(),
                    menu.getOrderableStatus());
    }

    @Transactional
    public Menu saveNewMenu(MenuDTO menuDTO) {

        Menu menu = new Menu();
        menu.setMenuCode(menuDTO.getMenuCode());
        menu.setMenuName(menuDTO.getMenuName());
        menu.setMenuPrice(menuDTO.getMenuPrice());
        menu.setCategoryCode(menuDTO.getCategoryCode());
        menu.setOrderableStatus(menuDTO.getOrderableStatus());

        return menuRepository.save(menu); // 저장된 메뉴를 반환
    }

    @Transactional
    public void deleteByMenu(int menuCode) {

        menuRepository.deleteByMenuCode(menuCode);

    }
}

