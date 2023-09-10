package com.shinhan.connector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendMoneyRequest {
    private String bankCode;
    private String accountNumber;
    private String depositorName;
    private Long amount;
}
