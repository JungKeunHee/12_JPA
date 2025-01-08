package com.ohgiraffers.mapping.section03.compositekey.idclass;

import jakarta.persistence.*;

@Entity
@Table(name = "tbl_cart")
@IdClass(CartCompositeKey.class) // 복합키로 설정할 클래스 명시
public class Cart {

    @Id
    @Column(name = "cart_owner")
    private int cartOwner;

    @Id
    @Column(name = "added_book")
    private int addedBook;

    @Column(name = "quantity")
    private int quantity; // 수량

    public Cart() {}

    public Cart(int cartOwner, int addedBook, int quantity) {
        this.cartOwner = cartOwner;
        this.addedBook = addedBook;
        this.quantity = quantity;
    }
}
