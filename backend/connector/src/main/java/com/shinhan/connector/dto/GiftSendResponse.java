package com.shinhan.connector.dto;

import com.shinhan.connector.entity.GiftSend;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftSendResponse {
    private Integer scheduleNo;
    private String name;
    private String category;
    private Long price;
    private String note;

    public static GiftSendResponse fromEntity(GiftSend giftSend) {
        return GiftSendResponse.builder()
                .scheduleNo(giftSend.getNo())
                .name(giftSend.getName())
                .category(giftSend.getCategory())
                .price(giftSend.getPrice())
                .note(giftSend.getNote())
                .build();
    }
}
