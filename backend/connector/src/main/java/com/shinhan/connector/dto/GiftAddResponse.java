package com.shinhan.connector.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftAddResponse {
    private Integer giftNo;
    private Integer scheduleNo;
    private String name;
    private String category;
    private Integer price;
    private String note;


}
