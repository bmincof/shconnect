package com.shinhan.connector.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shinhan.connector.dto.request.SearchCondition;
import com.shinhan.connector.entity.TributeReceive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.shinhan.connector.entity.QTributeReceive.tributeReceive;

@Repository
@RequiredArgsConstructor
public class TributeReceiveQueryDslRepository {

    private final JPAQueryFactory jpaQueryFactory;
    public List<TributeReceive> getListByCondition(SearchCondition searchCondition, Integer userNo) {

        return jpaQueryFactory.selectFrom(tributeReceive)
                .where(
                        sameUser(userNo),
                        sameFriend(searchCondition.getFriendNo()),
                        startDate(searchCondition.getStartDate()),
                        endDate(searchCondition.getEndDate())
                ).fetch();
    }

    public Long getAmountByCondition(SearchCondition searchCondition, Integer userNo) {
        return jpaQueryFactory.select(tributeReceive.amount.sum())
                .from(tributeReceive)
                .where(
                        sameUser(userNo),
                        sameFriend(searchCondition.getFriendNo()),
                        startDate(searchCondition.getStartDate()),
                        endDate(searchCondition.getEndDate())
                ).fetchOne();
    }

    private BooleanExpression endDate(Long endDate) {
        if (endDate == null)
            return null;

        return tributeReceive.mySchedule.date.loe(endDate);
    }

    private BooleanExpression startDate(Long startDate) {
        if (startDate == null)
            return null;

        return tributeReceive.mySchedule.date.goe(startDate);
    }

    private BooleanExpression sameUser(Integer userNo) {
        if (userNo == null)
            return null;

        return tributeReceive.mySchedule.member.no.eq(userNo);
    }

    private BooleanExpression sameFriend(Integer friendNo) {
        if (friendNo == null)
            return null;

        return tributeReceive.friend.no.eq(friendNo);
    }
}
