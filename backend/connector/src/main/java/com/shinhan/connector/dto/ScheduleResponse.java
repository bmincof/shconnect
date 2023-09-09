package com.shinhan.connector.dto;

import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import lombok.Data;

@Data
public class ScheduleResponse {
    private Integer scheduleNo;
    private Integer friendNo;
    private String name;
    private String content;
    private Long date;
    private RepeatCycle repeatCycle;
    private Boolean favorite;
    private Alarm alarm;

    public ScheduleResponse(Schedule schedule) {
        this.scheduleNo = schedule.getNo();
        this.friendNo = schedule.getFriend().getNo();
        this.name = schedule.getName();
        this.date = schedule.getDate();
        this.repeatCycle = schedule.getRepeatCycle();
        this.favorite = schedule.getFavorite();
        this.alarm = schedule.getAlarm();
    }
}
