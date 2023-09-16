package com.shinhan.connector.dto.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class SearchCondition {
    private String option;
    private Integer friendNo;
    private Boolean amount;
    private Long startDate;
    private Long endDate;
}
