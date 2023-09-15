package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.AccountHistory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountHistoryResponse {
    private String bankCode;
    private String accountNumber;
    private String depositorName;
    private Long modifiedAmount;
    private Long remainAmount;
    private String note;
    private Long date;
    public static AccountHistoryResponse entityToDto(AccountHistory history) {
        return AccountHistoryResponse.builder()
                .bankCode(history.getBankCode())
                .accountNumber(history.getAccountNumber())
                .depositorName(history.getDepositorName())
                .modifiedAmount(history.getModifiedAmount())
                .remainAmount(history.getRemainAmount())
                .note(history.getNote())
                .date(history.getDate())
                .build();
    }
}
