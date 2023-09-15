package com.shinhan.connector.dto.response;

import com.shinhan.connector.entity.MySchedule;
import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import lombok.Builder;
import lombok.Data;

@Data
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

    public static ScheduleAddResponse fromScheduleEntity(Schedule schedule) {
        return ScheduleAddResponse.builder()
                .scheduleNo(schedule.getNo())
                .friendNo(schedule.getFriend().getNo())
                .name(schedule.getName())
                .category(schedule.getCategory())
                .date(schedule.getDate())
                .content(schedule.getContent())
                .repeatCycle(schedule.getRepeatCycle())
                .favorite(schedule.getFavorite())
                .alarm(schedule.getAlarm())
                .build();
    }

    public static ScheduleAddResponse fromMyScheduleEntity(MySchedule mySchedule) {
        return ScheduleAddResponse.builder()
                .scheduleNo(mySchedule.getNo())
                .name(mySchedule.getName())
                .category(mySchedule.getCategory())
                .date(mySchedule.getDate())
                .content(mySchedule.getContent())
                .repeatCycle(mySchedule.getRepeatCycle())
                .favorite(mySchedule.getFavorite())
                .alarm(mySchedule.getAlarm())
                .build();
    }

}
