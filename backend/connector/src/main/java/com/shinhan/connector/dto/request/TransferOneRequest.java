package com.shinhan.connector.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferOneRequest {
    String bankCode;
    String accountNumber;
}
