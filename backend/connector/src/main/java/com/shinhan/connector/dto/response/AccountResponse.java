package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.Account;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponse {
    String accountNumber;
    String bankCode;
    String accountType;
    Long remainMoney;
    Long date;

    public static AccountResponse entityToDto(Account account) {
        return AccountResponse.builder()
                .accountNumber(account.getAccountNumber())
                .bankCode(account.getBankCode())
                .accountType(account.getType().name())
                .remainMoney(account.getRemainMoney())
                .date(account.getDate())
                .build();
    }
}
