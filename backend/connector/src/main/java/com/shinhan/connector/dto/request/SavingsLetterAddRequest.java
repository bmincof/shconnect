package com.shinhan.connector.dto.request;

import com.shinhan.connector.entity.Friend;
import com.shinhan.connector.entity.Member;
import com.shinhan.connector.entity.SavingsLetter;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class SavingsLetterAddRequest {
    Integer friendNo;
    String name;
    String content;
    String bankCode;
    String bankAccount;
    Integer totalRound;
    Long amount;
    Long paymentDate;
    Long startDate;
    Long endDate;

    public SavingsLetter toEntity(Member member, Friend friend) {
        return SavingsLetter.builder()
                .member(member)
                .friend(friend)
                .name(name)
                .content(content)
                .bankCode(bankCode)
                .bankAccount(bankAccount)
                .currentRound(1)
                .totalRound(totalRound)
                .amount(amount)
                .paymentDate(paymentDate)
                .startDate(startDate)
                .endDate(endDate)
                .build();
    }
}
