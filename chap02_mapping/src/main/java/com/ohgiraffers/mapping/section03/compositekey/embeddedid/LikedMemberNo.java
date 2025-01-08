package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LikedMemberNo {

    @Column(name = "liked_member_no")
    private int likedMemberNo;

    public LikedMemberNo () {}

    public LikedMemberNo(int likedMemberNo) {
        this.likedMemberNo = likedMemberNo;
    }
}
