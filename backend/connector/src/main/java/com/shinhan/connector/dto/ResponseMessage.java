package com.shinhan.connector.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// API 응답 메시지를 전달하는 DTO
@AllArgsConstructor
@Getter
public class ResponseMessage {
    private String message;
}
