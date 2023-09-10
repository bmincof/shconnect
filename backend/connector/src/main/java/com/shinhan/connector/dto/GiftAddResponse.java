package com.shinhan.connector.dto;

import com.shinhan.connector.entity.GiftReceive;
import com.shinhan.connector.entity.GiftSend;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftAddResponse {
    private Integer giftNo;
    private Integer scheduleNo;
    private String name;
    private String category;
    private Long price;
    private String note;

    // GiftSend 엔티티로부터 응답 DTO를 생성
    public static GiftAddResponse fromGiftSendEntity(GiftSend giftSend) {
        return GiftAddResponse.builder()
                .giftNo(giftSend.getNo())
                .scheduleNo(giftSend.getSchedule().getNo())
                .name(giftSend.getName())
                .category(giftSend.getCategory())
                .price(giftSend.getPrice())
                .note(giftSend.getNote())
                .build();
    }
}
