package com.shinhan.connector.dto;

import lombok.Data;

@Data
public class TransferOneCheckResponse {
    String accountHolder;

    public TransferOneCheckResponse(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}
