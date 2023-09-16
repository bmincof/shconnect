package com.shinhan.connector.dto.response;

import lombok.Data;

@Data
public class GiftAddResponse {
    private Integer giftNo;
    private Integer scheduleNo;
    private String name;
    private String category;
    private String note;

    public GiftAddResponse(Integer giftNo, Integer scheduleNo, String name, String category, String note) {
        this.giftNo = giftNo;
        this.scheduleNo = scheduleNo;
        this.name = name;
        this.category = category;
        this.note = note;
    }
}
