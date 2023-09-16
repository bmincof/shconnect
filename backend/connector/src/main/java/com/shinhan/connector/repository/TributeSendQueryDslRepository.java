package com.shinhan.connector.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.entity.TributeSend;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.shinhan.connector.entity.QTributeSend.tributeSend;

@Repository
@RequiredArgsConstructor
public class TributeSendQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public List<TributeSend> getListByCondition(SearchCondition searchCondition, Integer userNo) {

        return jpaQueryFactory.selectFrom(tributeSend)
                .where(
                        sameUser(userNo),
                        sameFriend(searchCondition.getFriend()),
                        startDate(searchCondition.getStart()),
                        endDate(searchCondition.getEnd())
                ).fetch();
    }

    public Long getAmountByCondition(SearchCondition searchCondition, Integer userNo) {
        return jpaQueryFactory.select(tributeSend.amount.sum())
                .from(tributeSend)
                .where(
                        sameUser(userNo),
                        sameFriend(searchCondition.getFriend()),
                        startDate(searchCondition.getStart()),
                        endDate(searchCondition.getEnd())
                ).fetchOne();
    }

    private BooleanExpression endDate(Long endDate) {
        if (endDate == null)
            return null;

        return tributeSend.schedule.date.loe(endDate);
    }

    private BooleanExpression startDate(Long startDate) {
        if (startDate == null)
            return null;

        return tributeSend.schedule.date.goe(startDate);
    }

    private BooleanExpression sameUser(Integer userNo) {
        if (userNo == null)
            return null;

        return tributeSend.schedule.member.no.eq(userNo);
    }

    private BooleanExpression sameFriend(Integer friendNo) {
        if (friendNo == null)
            return null;

        return tributeSend.schedule.friend.no.eq(friendNo);
    }
}
