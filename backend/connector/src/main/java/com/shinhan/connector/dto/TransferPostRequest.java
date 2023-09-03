package com.shinhan.connector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferPostRequest {
    String 입금은행코드;
    String 입금계좌번호;
    String 입금통장메모;
}
