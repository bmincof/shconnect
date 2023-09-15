package com.shinhan.connector.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
public class GiftResponse {
    // 일정/내 일정 번호
    private Integer scheduleNo;
    private String name;
    private String category;
    private String note;

    public GiftResponse(Integer scheduleNo, String name, String category, String note) {
        this.scheduleNo = scheduleNo;
        this.name = name;
        this.category = category;
        this.note = note;
    }
}
