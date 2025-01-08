package com.ohgiraffers.mapping.section03.compositekey.embeddedid;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

@SpringBootTest
public class EmbeddedIdTest {

    @Autowired
    private LikeService service;

    /* comment.
    *   복합키가 존재하는 테이블의 매핑 전략
    *   1. EmbeddedId
    *   - @Embeddable 클래스에 복합키를 정의하고
    *   - 사용할 엔티티에서 @Embedded 로 복합키 클래스를 매핑한다.
    *  */

    private static Stream<Arguments> getInfo() {
        return Stream.of(
          Arguments.of(1, 1),
          Arguments.of(1, 2),
          Arguments.of(2, 1),
          Arguments.of(2, 2)
        );
    }

    @ParameterizedTest(name = "{0}번 회원이 {1}번 책 좋아요 등록 테스트")
    @MethodSource("getInfo")
    void testComposite(int memberNo, int bookNo) {
        Assertions.assertDoesNotThrow(
                () -> service.registlikeInfo(
                        new LikeDTO(memberNo, bookNo)
                )
        );
    }
}
