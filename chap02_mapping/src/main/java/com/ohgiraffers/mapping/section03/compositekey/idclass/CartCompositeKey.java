package com.ohgiraffers.mapping.section03.compositekey.idclass;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CartCompositeKey {

    private int cartOwner; // 카트 주인
    private int addedBook; // 추가 된 책

}
