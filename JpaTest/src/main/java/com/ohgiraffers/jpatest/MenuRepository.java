package com.ohgiraffers.jpatest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

    Menu findByMenuCode(int menuCode);

    void deleteByMenuCode(int menuCode); // 반환 타입을 void로 변경
}
