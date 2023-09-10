package com.shinhan.connector.dto;

import com.shinhan.connector.entity.AccountHistory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountHistoryResponse {
    private Long date;
    public static AccountHistoryResponse entityToDto(AccountHistory history) {
        return AccountHistoryResponse.builder()
                .date(history.getDate())
                .build();
    }
}
