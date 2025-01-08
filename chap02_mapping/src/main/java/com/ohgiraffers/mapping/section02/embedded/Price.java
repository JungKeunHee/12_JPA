package com.ohgiraffers.mapping.section02.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// 다른 곳에서 사용 가능하게 설정
@Embeddable
public class Price {

    // 출판 가격(10000), 할인율(10), 판매 가격(9000)
    @Column(name = "regular_price")
    private int regularPrice; // 정식 판매 가격

    @Column(name = "discount_rate")
    private double discountRate; // 할인율

    @Column(name = "sell_price")
    private int sellPrice; // 할인율 적용된 실제 가격

    public Price () {}

    public Price(int regularPrice, double discountRate, int sellPrice) {
        this.regularPrice = regularPrice;
        this.discountRate = discountRate;
        this.sellPrice = sellPrice;
    }

    public Price(int regularPrice, double discountRate) {
        this.regularPrice = regularPrice;
        this.discountRate = discountRate;
    }
}
