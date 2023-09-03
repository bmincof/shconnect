package com.shinhan.connector.dto;

import lombok.Data;

@Data
public class ResponseResult {
    private boolean result;

    public ResponseResult(boolean result) {
        this.result = result;
    }
}
