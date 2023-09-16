package com.shinhan.connector.dto.request;

import lombok.Data;

@Data
public class RecommendCondition {
    private Boolean age;
    private Boolean gender;
    private String category;
}
