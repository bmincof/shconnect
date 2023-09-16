package com.shinhan.connector.dto.request;

import lombok.Data;

@Data
public class SavingsLetterModifyRequest {
    private String name;
    private String content;
    private Long amount;
    private Long paymentDate;
    private Long endDate;
}
