package com.ohgiraffers.associationmapping.section03.bidirection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class BiTest {

    @Autowired
    private BiService service;

    /* comment.
    *   데이터베이스의 테이블은 외래키 하나로 양방향
    *   조회가 가능하다. (JOIN)
    *   하지만, 객체는 서로 다른 두 단방향 참조를
    *   합쳐서 양방향 이라고 한다.
    *   따라서 두 개의 연관관계 중 주인을 설정(mapped by)
    *   즉 주인을 설정하는 기준은
    *   외래키를 관리하는(가지고 있는) 엔티티가 주인이\
    *   되어야 한다. 따라서 우리가 쓰는 테이블 따지면
    *   Menu 가 categoryCode 를 가지고 있기 때문에 주인
    *  */

    @DisplayName("양방향 연관관계 매핑 조회")
    @Test
    void ownerFindTest() {

        int menuCode = 10;

        /* comment.
        *   Menu 엔티티가 주인인 관계에서 조회 시
        *   처음 조회부터 바로 조인한 결과를 가져오게 된다.
        *  */

        Menu foundMenu = service.findMenu(menuCode);

        Assertions.assertEquals(menuCode, foundMenu.getMenuCode());
    }
    @DisplayName("양방향 연관관계 매핑 조회(주인이 아닌 엔티티로 조회)")
    @Test
    void slaveFindTest() {

        int categoryCode = 10;
        // 주인이 아닌 객체 조회 시
        // 해당 엔티티를 먼저 조회 후, 필요 시 연관 된 엔티티 조회하는 쿼리 실행
        Category foundCategory = service.findCategory(categoryCode);

        Assertions.assertEquals(categoryCode, foundCategory.getCategoryCode());
    }

    private static Stream<Arguments> getMenuInfo() {
        return Stream.of(
                Arguments.of(111, "스테이크", 9800, "Y")
        );
    }

    @ParameterizedTest
    @DisplayName("양방향 연관관계 주인 객체를 이용한 삽입 테스트")
    @MethodSource("getMenuInfo")
    void ownerInsert(int menuCode, String menuName, int menuPrice, String orderableStatus) {
        // 메뉴에 집어넣을 Category 객체 조회
        Category category = service.findCategory(4);

        Menu newMenu = new Menu(menuCode, menuName, menuPrice, category,orderableStatus);

        Assertions.assertDoesNotThrow(
                () -> service.registMenu(newMenu)
        );
    }

    private static Stream<Arguments> getCategoryInfo() {
        return Stream.of(
                Arguments.of(112, "양방향테스트", null)
        );
    }

    @DisplayName("양방향 연관관계 주인이 아닌 객체 이용한 insert")
    @ParameterizedTest
    @MethodSource("getCategoryInfo")
    void slaveInsert(int categoryCode, String categoryName, Integer ref) {

        Category category = new Category(
                categoryCode,
                categoryName,
                ref
        );

        service.registCategory(category);

        Category foundCategory = service.findCategory(categoryCode);

        Assertions.assertNotNull(foundCategory);
    }
}
