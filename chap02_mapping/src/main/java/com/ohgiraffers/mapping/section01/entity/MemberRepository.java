package com.ohgiraffers.mapping.section01.entity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/* comment.
*   @Mapper DB Access 계층이 마이바티스 전용
*   JPA 를 사용할 때는 @Repository 어노테이션을
*   사용하게 된다.
*  */

@Repository
public class MemberRepository {

    /* Entity 매니저를 주입 받아 영속성 컨텍스트가
    *   우리가 만든 Member 를 관리할 수 있도록 한다.
    *  */
    @PersistenceContext
    private EntityManager manager;

    public void save(Member member) {

        manager.persist(member);

    }
}
