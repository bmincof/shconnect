package com.shinhan.connector.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shinhan.connector.dto.request.SearchCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.shinhan.connector.entity.QGiftReceive.giftReceive;

@Repository
@RequiredArgsConstructor
public class GiftReceiveQueryDslRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<Long> getAmountByCondition(SearchCondition searchCondition, Integer userNo) {
        return Optional.ofNullable(jpaQueryFactory.select(
                        giftReceive.priceMax.sum().add(giftReceive.priceMin.sum()))
                .from(giftReceive)
                .where(
                        sameUser(userNo),
                        startDate(searchCondition.getStart()),
                        endDate(searchCondition.getEnd())
                ).fetchOne());
    }

    private BooleanExpression endDate(Long endDate) {
        if (endDate == null)
            return null;

        return giftReceive.mySchedule.date.loe(endDate);
    }

    private BooleanExpression startDate(Long startDate) {
        if (startDate == null)
            return null;

        return giftReceive.mySchedule.date.goe(startDate);
    }

    private BooleanExpression sameUser(Integer userNo) {
        if (userNo == null)
            return null;

        return giftReceive.mySchedule.member.no.eq(userNo);
    }
}
