package com.shinhan.connector.dto.request;

import com.shinhan.connector.enums.Gender;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecommendCondition {
    private Integer ageRange;
    private Gender gender;
    private String category;
}
