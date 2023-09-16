package com.shinhan.connector.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.entity.Schedule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shinhan.connector.entity.QSchedule.schedule;

@Repository
@RequiredArgsConstructor
public class ScheduleQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public List<Schedule> getListByCondition(SearchCondition searchCondition, Integer userNo) {
        return jpaQueryFactory.selectFrom(schedule)
                .where(
                        sameUser(userNo),
                        startDate(searchCondition.getStart()),
                        endDate(searchCondition.getEnd())
                ).fetch();
    }

    private BooleanExpression endDate(Long endDate) {
        if (endDate == null)
            return null;

        return schedule.date.loe(endDate);
    }

    private BooleanExpression startDate(Long startDate) {
        if (startDate == null)
            return null;

        return schedule.date.goe(startDate);
    }

    private BooleanExpression sameUser(Integer userNo) {
        if (userNo == null)
            return null;

        return schedule.member.no.eq(userNo);
    }
}
