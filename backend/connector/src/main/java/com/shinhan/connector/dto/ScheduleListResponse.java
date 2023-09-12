package com.shinhan.connector.dto;

import com.shinhan.connector.entity.MySchedule;
import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import lombok.Builder;
import lombok.Data;

// 일정 목록을 조회했을 때 반환할 응답
@Data
@Builder
public class ScheduleListResponse {
    private Integer scheduleNo;
    private String name;
    private String content;
    private Long date;
    private RepeatCycle repeatCycle;
    private Boolean favorite;
    private Alarm alarm;
    private FriendResponse friend;

    public static ScheduleListResponse fromScheduleEntity(Schedule schedule) {
        return ScheduleListResponse.builder()
                .scheduleNo(schedule.getNo())
                .name(schedule.getName())
                .content(schedule.getContent())
                .date(schedule.getDate())
                .repeatCycle(schedule.getRepeatCycle())
                .favorite(schedule.getFavorite())
                .alarm(schedule.getAlarm())
                .friend(FriendResponse.fromEntity(schedule.getFriend()))
                .build();
    }

    public static ScheduleListResponse fromMyScheduleEntity(MySchedule mySchedule) {
        return ScheduleListResponse.builder()
                .scheduleNo(mySchedule.getNo())
                .name(mySchedule.getName())
                .content(mySchedule.getContent())
                .date(mySchedule.getDate())
                .repeatCycle(mySchedule.getRepeatCycle())
                .favorite(mySchedule.getFavorite())
                .alarm(mySchedule.getAlarm())
                .build();
    }
}
