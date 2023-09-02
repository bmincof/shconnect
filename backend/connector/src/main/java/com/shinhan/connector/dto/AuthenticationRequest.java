package com.shinhan.connector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequest {
    String bankCode;
    String accountNumber;
}
