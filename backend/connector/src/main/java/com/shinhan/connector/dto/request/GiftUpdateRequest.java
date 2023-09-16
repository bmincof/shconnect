package com.shinhan.connector.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftUpdateRequest {
    private String name;
    private String category;
    private Long price;
    private Long priceMin;
    private Long priceMax;
    private String note;
}
