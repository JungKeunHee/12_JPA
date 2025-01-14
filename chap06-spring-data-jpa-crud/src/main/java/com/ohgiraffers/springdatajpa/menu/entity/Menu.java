package com.ohgiraffers.springdatajpa.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tbl_menu")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
//@Builder(toBuilder = true) // Builder 기능 사용
public class Menu {

    @Id
    @Column(name = "menu_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int menuCode;

    @Column(name = "menu_name")
    private String menuName;

    @Column(name = "menu_price")
    private int menuPrice;

    @Column(name = "category_code")
    private int categoryCode;

    @Column(name = "orderable_status")
    private String orderableStatus;

    /* 3. builder 패턴 직접 구현 */
    public Menu menuName(String var) {
        this.menuName = var;
        return this;
    }

    public Menu builder() {
        return new Menu(menuCode, menuName, menuPrice, categoryCode, orderableStatus);
    }

}
