package com.shinhan.connector.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScheduleUpdateRequest {
    private String name;
    private String category;
    private Long date;
    private Integer repeatCycle;
    private String content;
    private Integer alarm;
}
