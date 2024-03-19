package com.bit.envdev.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QueryDslConfiguration {
    // 영속성컨텍스트 - 매니저 선언 (컨테이너를 관리하는 매니저라고 생각하면됨)
    @PersistenceContext
    private EntityManager em;

    // JPAQueryFactory bean 객체로 등록
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }
}
