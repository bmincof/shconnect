package com.shinhan.connector.dto;

import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class ScheduleAddResponse {
    private Integer scheduleNo;
    private Integer friendNo;
    private String name;
    private String category;
    private Long date;
    private String content;
    private RepeatCycle repeatCycle;
    private Boolean favorite;
    private Alarm alarm;
}
