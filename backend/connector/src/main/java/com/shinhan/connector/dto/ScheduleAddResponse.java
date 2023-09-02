package com.shinhan.connector.dto;

import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
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

    public ScheduleAddResponse(Schedule schedule) {
        this.scheduleNo = schedule.getNo();
        this.friendNo = schedule.getFriend().getNo();
        this.name = schedule.getName();
        this.category = schedule.getCategory();
        this.date = schedule.getDate();
        this.content = schedule.getContent();
        this.repeatCycle = schedule.getRepeatCycle();
        this.favorite = schedule.getFavorite();
        this.alarm = schedule.getAlarm();
    }
}
