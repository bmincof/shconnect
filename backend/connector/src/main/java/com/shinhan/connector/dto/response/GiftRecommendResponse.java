package com.shinhan.connector.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GiftRecommendResponse {
    private String category;
    private Long price;
}
