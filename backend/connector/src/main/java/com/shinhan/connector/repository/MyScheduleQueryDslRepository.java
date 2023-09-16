package com.shinhan.connector.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.entity.MySchedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shinhan.connector.entity.QMySchedule.mySchedule;

@Repository
@RequiredArgsConstructor
public class MyScheduleQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<MySchedule> getListByCondition(SearchCondition searchCondition, Integer userNo) {
        return jpaQueryFactory.selectFrom(mySchedule)
                .where(
                        sameUser(userNo),
                        startDate(searchCondition.getStart()),
                        endDate(searchCondition.getEnd())
                ).fetch();
    }

    private BooleanExpression endDate(Long endDate) {
        if (endDate == null)
            return null;

        return mySchedule.date.loe(endDate);
    }

    private BooleanExpression startDate(Long startDate) {
        if (startDate == null)
            return null;

        return mySchedule.date.goe(startDate);
    }

    private BooleanExpression sameUser(Integer userNo) {
        if (userNo == null)
            return null;

        return mySchedule.member.no.eq(userNo);
    }
}
