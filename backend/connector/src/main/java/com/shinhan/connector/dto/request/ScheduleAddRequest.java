package com.shinhan.connector.dto.request;

import com.shinhan.connector.entity.MySchedule;
import com.shinhan.connector.entity.Schedule;
import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import lombok.*;

import java.util.NoSuchElementException;

@Data
@Builder
public class ScheduleAddRequest {
    // 지인 식별번호
    private Integer friendNo;
    // 일정 이름
    private String name;
    // 일정 분류
    private String category;
    // 날짜
    private Long date;
    // 반복 주기
    private Integer repeatCycle;
    // 내용
    private String content;
    // 알람 여부
    private Integer alarm;
    private String memberId;

    public Schedule toScheduleEntity() {
        return Schedule.builder()
                .name(this.getName())
                .content(this.getContent())
                .category(this.getCategory())
                .date(this.getDate())
                .repeatCycle(RepeatCycle.getRepeatCycle(this.getRepeatCycle()))
                .favorite(false)
                .alarm(Alarm.getAlarm(this.getAlarm()))
                .build();
    }

    public MySchedule toMyScheduleEntity() {
        return MySchedule.builder()
                .name(this.getName())
                .content(this.getContent())
                .category(this.getCategory())
                .date(this.getDate())
                .repeatCycle(RepeatCycle.getRepeatCycle(this.getRepeatCycle()))
                .favorite(false)
                .alarm(Alarm.getAlarm(this.getAlarm()))
                .build();
    }
}
