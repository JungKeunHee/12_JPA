package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import jakarta.persistence.Embedded;

public class LikedCompositkey {

    @Embedded
    private LikedMemberNo likedMemberNo;

    @Embedded
    private LikedBookNo likedBookNo;

    public LikedCompositkey() {}

    public LikedCompositkey(LikedMemberNo likedMemberNo, LikedBookNo likedBookNo) {
        this.likedMemberNo = likedMemberNo;
        this.likedBookNo = likedBookNo;
    }
}
