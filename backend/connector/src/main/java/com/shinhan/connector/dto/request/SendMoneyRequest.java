package com.shinhan.connector.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMoneyRequest {
    private Integer tributeNo;
    private Integer savingsLetterNo;
    private String bankCode;
    private String accountNumber;
    private String depositorName;
    private Long amount;
}
