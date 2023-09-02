package com.shinhan.connector.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor
@Getter
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
}
