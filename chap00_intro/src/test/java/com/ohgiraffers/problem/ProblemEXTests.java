package com.ohgiraffers.problem;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProblemEXTests {

    /* title. 테스트 코드 기반으로 JPA 를 사용하지 않을 때 문제 EX */

    /* comment.
    *   테스트 전용 클래스란?
    *   @Test 라는 어노테이션이 1개 이상 가지고 있는 클래스를 의미한다.
    *   테스트 메소드는 반환값이 없으며, 따라서 void 형으로 작성한다.
    *   독립적인 실행이 가능하며, 기능을 테스트 하는 것에 초점이 있다.
    *   또한, 접근제한자는 사용하지 않아도 되지만, private 는 안된다.
    *  */

    private Connection con;

    // 우리가 작성한 Test 메소드 실행 전에 1번씩 동작을 하는 역할
    @BeforeEach
    void setConnection() throws ClassNotFoundException, SQLException {
        // JDBC Connection 을 만들기 위한 DB 정도
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/menudb";
        String user = "ohgiraffers";
        String password = "ohgiraffers";

        // BeforeEach 가 동작하는 지 확인용 sout
        System.out.println("BeforeEach 동작 확인");

        // DB 와 접속하기 위한 통로에 우리 DB 정보 전달
        Class.forName(driver);
        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        System.out.println("AfterEach 동작 확인");
        con.rollback();
        con.close();
    }

    /* comment.
    *   JDBC 를 직접적으로 사용 시 문제점 확인.
    *   1. 데이터 변환, SQL 문 작성, JDBC 코드 중복 작성 문제
    *   - 개발 시간 증가, 불필요한 코드 많음, 유지보수성 악화
    *   2. SQL 에 의존적인 개발
    *  */

    /* index. 1번 문제 확인 */
    @Test
    @DisplayName("직접 SQL 문을 작성해서 메뉴 조회 시 문제 확인")
    void testDirectSQL() throws SQLException {
        String query = "SELECT * FROM TBL_MENU";

        Statement stmt = con.createStatement();

        ResultSet rest = stmt.executeQuery(query);

        // DB 조회한 메뉴들을 담기 위한 공간 생성
        List<Menu> menuList = new ArrayList<>();

        while (rest.next()){
            Menu menu = new Menu();
            menu.setMenuCode(rest.getInt("menu_code"));
            menu.setMenuName(rest.getString("menu_name"));
            menu.setMenuPrice(rest.getInt("menu_price"));
            menu.setCategoryCode(rest.getInt("category_code"));
            menu.setOrderableStatus(rest.getString("orderable_status"));

            menuList.add(menu);
        }
        /* comment.
        *   Test 클래스는 검증을 위한 클래스이다.
        *   Assertions 클래스는 Test 를 위한 검증할 수 있는
        *   메소드를 제공해준다.
        *  */

        Assertions.assertNotNull(menuList);

        menuList.forEach(menu -> System.out.println("menu = " + menu));

        rest.close();
        stmt.close();
    }

    /* index. 2. SQL 의존적인 개발 */

    /* comment.
    *   고객 즉 클라이언트 측에서 요구사항이 변했을 때
    *   EX) 메뉴 이름에서 메뉴 가격만 조회하는 걸로 수정해주세요~
    *   혹은 컬럼 추가해주세요~
    *   이 상황이 발생하면 DB DDL 수정, SQL 문 수정, Application 코드 수정 필요
    *  */

    /* index. 3. 패러다임 불일치 문제(객체지향 관점에서 벗어남, 상속, 다형성, 캡슐화, 추상화) */

    /* comment.
    *   SQL 관점에서의 1:N -> 엔티티 간의 연관성을 FK 로 가지고 있음
    *   JAVA 관점에서의 1:N -> 연관성을 자신의 멤버로 가지게 된다.
    *  */

    /* index. 4. 동일성 보장의 문제 */
    @Test
    @DisplayName("조회한 두 개의 행을 담은 객체의 동일성 비교")
    void testEquals() throws SQLException {

        String query = "SELECT MENU_CODE, MENU_NAME FROM TBL_MENU WHERE MENU_CODE = 1";

        Statement stmt1 = con.createStatement();
        ResultSet rest1 = stmt1.executeQuery(query);

        Menu menu1 = null;

        while (rest1.next()) {
            menu1 = new Menu();
            menu1.setMenuCode(rest1.getInt("MENU_CODE"));
            menu1.setMenuName(rest1.getString("MENU_NAME"));
        }

        Statement stmt2 = con.createStatement();
        ResultSet rest2 = stmt2.executeQuery(query);

        Menu menu2 = null;

        while (rest2.next()) {
            menu2 = new Menu();
            menu2.setMenuCode(rest2.getInt("MENU_CODE"));
            menu2.setMenuName(rest2.getString("MENU_NAME"));
        }

        System.out.println(menu1.hashCode());
        System.out.println(menu2.hashCode());

        /* comment.
        *   동일한 DB 에서 같은 ROW 에 해당하는 데이터를 꺼냈는데
        *   각기 다른 객체에 담았을 때 생기는 동일성 보장 실패
        *   -> 수정 시 다른 곳에서 파악이 불가능!!!!
        *  */

        Assertions.assertTrue(menu1 == menu2);

        rest1.close();
        rest2.close();
        stmt1.close();
        stmt2.close();
    }
}
