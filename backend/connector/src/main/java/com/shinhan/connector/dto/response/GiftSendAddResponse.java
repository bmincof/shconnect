package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.GiftSend;
import lombok.Getter;

@Getter
public class GiftSendAddResponse extends GiftAddResponse {
    private Long price;

    public GiftSendAddResponse(GiftSend giftSend) {
        super(giftSend.getNo(), giftSend.getSchedule().getNo(), giftSend.getCategory(),
                giftSend.getName(), giftSend.getNote());
        this.price = giftSend.getPrice();
    }
}
