package com.shinhan.connector.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferOneCheckRequest {
    String bankCode;
    String accountNumber;
    String confirm;
}
