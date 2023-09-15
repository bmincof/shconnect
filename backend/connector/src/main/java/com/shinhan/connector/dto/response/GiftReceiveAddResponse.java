package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.GiftReceive;
import lombok.Getter;

@Getter
public class GiftReceiveAddResponse extends GiftAddResponse {
    private Long priceMin;
    private Long priceMax;

    public GiftReceiveAddResponse(GiftReceive giftReceive) {
        super(giftReceive.getNo(), giftReceive.getMySchedule().getNo(), giftReceive.getName(), giftReceive.getCategory(), giftReceive.getNote());
        this.priceMin = giftReceive.getPriceMin();
        this.priceMax = giftReceive.getPriceMax();
    }
}
