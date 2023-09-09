package com.shinhan.connector.dto;

import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import lombok.Data;

// 일정 목록을 조회했을 때 반환할 응답
@Data
public class ScheduleListResponse {
    private Integer scheduleNo;
    private String name;
    private String content;
    private Long date;
    private RepeatCycle repeatCycle;
    private Boolean favorite;
    private Alarm alarm;
    private FriendResponse friend;

    public ScheduleListResponse(Schedule schedule) {
        this.scheduleNo = schedule.getNo();
        this.name = schedule.getName();
        this.content = schedule.getContent();
        this.date = schedule.getDate();
        this.repeatCycle = schedule.getRepeatCycle();
        this.favorite = schedule.getFavorite();
        this.alarm = schedule.getAlarm();
        this.friend = FriendResponse.fromEntity(schedule.getFriend());
    }
}
