package com.ohgiraffers.section03.entity;

import com.ohgiraffers.section01.entitymanager.EntityManagerGenerator;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class LifeCycleTest {

    private EntityLifeCycle lifeCycle;

    @BeforeEach
    void setLifeCycle(){
        this.lifeCycle = new EntityLifeCycle();
    }

    @ParameterizedTest
    @DisplayName("비영속 테스트(관리하지 않음)")
    @ValueSource(ints = {1, 2})
    void testNotManaged(int menuCode){

        Menu foundMenu = lifeCycle.findMenuByMenuCode(menuCode);

        Menu newMenu = new Menu(
                foundMenu.getMenuCode(),
                foundMenu.getMenuName(),
                foundMenu.getMenuPrice(),
                foundMenu.getCategoryCode(),
                foundMenu.getOrderableStatus()
        );

        Assertions.assertNotEquals(foundMenu, newMenu);
    }

    @ParameterizedTest
    @DisplayName("다른 엔티티 매니저가 관리하는 영속성 테스트")
    @ValueSource(ints = {1, 2})
    void testOtherManager(int menuCode) {
        Menu menu1 = lifeCycle.findMenuByMenuCode(menuCode);
        Menu menu2 = lifeCycle.findMenuByMenuCode(menuCode);

        Assertions.assertEquals(menu1, menu2);
    }

    @ParameterizedTest
    @DisplayName("같은 엔티티 매니저가 관리하는 영속성 테스트")
    @ValueSource(ints = {1, 2})
    void testSameManager(int menuCode){
        EntityManager manager = EntityManagerGenerator.getInstance();

        Menu menu1 = manager.find(Menu.class, menuCode);
        Menu menu2 = manager.find(Menu.class, menuCode);

        Assertions.assertEquals(menu1, menu2);
    }

    /* comment.
    *   엔티티 매니저가 영속성 컨텍스트에 엔티티 객체를 저장(persist)
    *   하게 된다면 영속성 컨텍스트가 관리할 수 있게 되며,
    *   이를 영속 상태라고 한다.
    *   find(), createQuery() 등을 사용한 조회도 자동으로 영속 상태가 된다.
    *   영속 상태인 엔티티 객체는 PK 로 조회를 하면 이미 관리가 되고 있기
    *   때문에 같은 객체(인스턴스) 를 반환하게 된다.
    *  */

    @ParameterizedTest
    @DisplayName("준영속화 detach 테스트")
    @CsvSource({"13, 1000", "14, 1000"})
    void testDetachEntity(int menuCode, int menuPrice){

        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class, menuCode);

        transaction.begin();

        // detach : 특정 엔티티만 준영속 상태(관련하지 않는 상태) 로 변경
        manager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);
        manager.flush();

        Assertions.assertEquals(menuPrice, manager.find(Menu.class, menuCode).getMenuPrice());
        transaction.rollback();
    }

    @ParameterizedTest
    @DisplayName("준영속화 detach 후 재영속화(merge) 테스트")
    @CsvSource({"13, 1000", "14, 1000"})
    void testDetachAndPersist(int menuCode, int menuPrice){

        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class, menuCode);

        transaction.begin();

        // detach : 특정 엔티티만 준영속 상태(관련하지 않는 상태) 로 변경
        manager.detach(foundMenu);
        foundMenu.setMenuPrice(menuPrice);

        // merge : 관리하지 않는 엔티티를 다시 관리 요청
        manager.merge(foundMenu);

        /* comment.
        *   파라미터로 넘어 온 foundMenu 준영속 엔티티 객체의 식별자 값으로
        *   1차 캐시에서 조회, 만약 1차 캐시에 엔티티가 없으면 DB 에서 엔티티
        *   조회 후 1차 캐시에 저장한다.
        *   조회한 영속 엔티티 객체에 준영속 상태의 엔ㅌ티티 객체의 값을 병합(merge)
        *   한 뒤 영속 엔티티 객체를 반환하게 된다.
        *  */
        manager.flush();

        Assertions.assertEquals(menuPrice, manager.find(Menu.class, menuCode).getMenuPrice());
        transaction.rollback();
    }

    @ParameterizedTest
    @DisplayName("영속성 엔티티 삭제 remove 테스트")
    @ValueSource(ints = {1})
    void testRemoveEntity(int menuCode) {

        EntityManager manager = EntityManagerGenerator.getInstance();

        EntityTransaction transaction = manager.getTransaction();

        Menu foundMenu = manager.find(Menu.class, menuCode);

        transaction.begin();

        /* comment.
        *   remove() 엔티티를 영속성 컨텍스트 및 데이터 베이스에서
        *   제거한다.
        *   단 트랜젝션을 제어하지 않으면 데이터베이스에 영구 반영되지는
        *   않는다.
        *   트렌젝션을 커밋, 플러쉬 하는 순간 영속성 컨텍스트 에서 관리하는
        *   엔티티 객체를 DB 에 반영을 하게 된다.
        *  */

        manager.remove(foundMenu);
        // remove 반영
        manager.flush();

        // 같은 메뉴코드로 동일한 데이터 찾아지는 지 확인
        Menu refoundMenu = manager.find(Menu.class, menuCode);

        Assertions.assertNull(refoundMenu);
    }
}
