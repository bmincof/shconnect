package com.shinhan.connector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationCheckResquest {
    String bankCode;
    String accountNumber;
    String confirm;
}
