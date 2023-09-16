package com.shinhan.connector.dto.request;

import lombok.Data;

@Data
public class SearchCondition {
    private String option;
    private Integer friend;
    private Boolean amount;
    private Long start;
    private Long end;
    private Boolean age;
    private Boolean gender;
    private String category;
}
