package com.shinhan.connector.config.jwt;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}