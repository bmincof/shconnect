package com.shinhan.connector.dto.response;

import lombok.Data;

@Data
public class TransferOneCheckResponse {
    String accountHolder;

    public TransferOneCheckResponse(String accountHolder) {
        this.accountHolder = accountHolder;
    }
}
