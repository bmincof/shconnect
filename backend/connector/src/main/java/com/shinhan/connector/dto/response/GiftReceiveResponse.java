package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.GiftReceive;
import lombok.Getter;

@Getter
public class GiftReceiveResponse extends GiftResponse {
    private Integer friendNo;
    private Long priceMin;
    private Long priceMax;

    public GiftReceiveResponse(GiftReceive giftReceive) {
        super(giftReceive.getNo(), giftReceive.getName(), giftReceive.getCategory(), giftReceive.getNote());
        this.friendNo = giftReceive.getFriend().getNo();
        this.priceMin = giftReceive.getPriceMin();
        this.priceMax = giftReceive.getPriceMax();
    }
}
