package com.ohgiraffers.mapping.section01.entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@SpringBootTest
public class EntityMappingTest {

    /* title. Entity Mapping 관련 어노테이션 */

    /* comment.
    *   JPA 의 핵심은 엔티티와 테이블을 매핑하는 것이다.
    *   -> Entity 클래스를 DB 테이블 화 시키는 것
    *   1. 객체와 테이블 매핑(@Entity, @Table)
    *   2. 기본키(pk 식별자) 매핑(@Id)
    *   3. 필드와 테이블 컬럼 매핑(@Colum)
    *   4. 연관관계 매핑(@ManyToOne, @OneToMany, @OneToOne, @JoinColum)
    *  */

    @Autowired
    private MemberService memberService;

    private static Stream<Arguments> getMember() {
        return Stream.of(
                Arguments.of(
                        1,
                        "user01",
                        "pass01",
                        "너구리",
                        "010-5518-2290",
                        "수원시 노진구",
                        LocalDateTime.now(),
                        "ROLE_MEMBER",
                        "Y"
                ),
                Arguments.of(
                        2,
                        "user02",
                        "pass02",
                        "코알라",
                        "010-1234-1234",
                        "수원시 핑구",
                        LocalDateTime.now(),
                        "ROLE_ADMIN",
                        "Y"
                )
        );
    }

    @ParameterizedTest
    @DisplayName("테이블 DDL 테스트")
    @MethodSource("getMember")
    void testCreateTable(int memberNo, String memberId, String memberPwd,
                         String memberName, String phone, String address,
                         LocalDateTime enrollDate, MemberRole memberRole, String status) {

        MemberRegistDTO newMember = new MemberRegistDTO(
                                    memberId, memberPwd,
                                    memberName, phone, address,
                                    enrollDate, memberRole, status
                                    );

        // 메소드 검증 시 사용하는 메소드, 해당 메소드가
        // Throw 예외를 발생시키는 지 확인한다.
        Assertions.assertDoesNotThrow(
                () -> memberService.registMember(newMember)
        );
    }
}
