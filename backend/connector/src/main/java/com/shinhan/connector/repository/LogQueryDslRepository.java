package com.shinhan.connector.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LogQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;


}
