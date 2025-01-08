package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LikedBookNo {

    @Column(name = "liked_book_no")
    private int likedBookNo;

    public LikedBookNo() {}

    public LikedBookNo(int likedBookNo) {
        this.likedBookNo = likedBookNo;
    }
}
