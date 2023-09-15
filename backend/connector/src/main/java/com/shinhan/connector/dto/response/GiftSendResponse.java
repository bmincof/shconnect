package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.GiftSend;
import lombok.Getter;

@Getter
public class GiftSendResponse extends GiftResponse {
    private Long price;

    public GiftSendResponse(GiftSend giftSend) {
        super(giftSend.getNo(), giftSend.getName(), giftSend.getCategory(), giftSend.getNote());
        this.price = giftSend.getPrice();
    }

//    public static GiftSendResponse fromEntity(GiftSend giftSend) {
//        return GiftSendResponse.builder()
//                .scheduleNo(giftSend.getNo())
//                .name(giftSend.getName())
//                .category(giftSend.getCategory())
//                .note(giftSend.getNote())
//                .build();
//    }
}
