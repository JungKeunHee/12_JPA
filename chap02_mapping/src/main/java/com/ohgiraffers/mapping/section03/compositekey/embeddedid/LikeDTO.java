package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class LikeDTO {

    private int likedMemberNo;
    private int likedBookNo;

}
