package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.SavingsLetter;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SavingsLetterResponse {
    private Integer savingsLetterNo;
    private Integer friendNo;
    private String name;
    private String content;
    private String bankCode;
    private String bankAccount;
    private Integer currentRound;
    private Integer totalRound;
    private Long amount;
    private Long paymentDate;
    private Long startDate;
    private Long endDate;

    public static SavingsLetterResponse entityToDto(SavingsLetter savingsLetter) {
        return SavingsLetterResponse.builder()
                .savingsLetterNo(savingsLetter.getNo())
                .friendNo(savingsLetter.getFriend() == null ? null : savingsLetter.getFriend().getNo())
                .name(savingsLetter.getName())
                .content(savingsLetter.getContent())
                .bankCode(savingsLetter.getBankCode())
                .bankAccount(savingsLetter.getBankAccount())
                .currentRound(savingsLetter.getCurrentRound())
                .totalRound(savingsLetter.getTotalRound())
                .amount(savingsLetter.getAmount())
                .paymentDate(savingsLetter.getPaymentDate())
                .startDate(savingsLetter.getStartDate())
                .endDate(savingsLetter.getEndDate())
                .build();
    }
}
