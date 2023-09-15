package com.shinhan.connector.dto.request;

import com.shinhan.connector.entity.GiftReceive;
import com.shinhan.connector.entity.GiftSend;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftAddRequest {
    private Integer scheduleNo;
    private Integer friendNo;
    private String name;
    private String category;
    private Long price;
    private Long priceMin;
    private Long priceMax;
    private String note;

    public GiftSend toGiftSendEntity() {
        return GiftSend.builder()
                .name(this.name)
                .category(this.category)
                .price(this.price)
                .note(this.note)
                .build();
    }

    public GiftReceive toGiftReceiveEntity() {
        return GiftReceive.builder()
                .name(this.name)
                .category(this.category)
                .priceMin(this.priceMin)
                .priceMax(this.priceMax)
                .note(this.note)
                .build();
    }
}
