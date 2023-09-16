package com.shinhan.connector.entity;

import com.shinhan.connector.config.TimeUtils;
import com.shinhan.connector.dto.request.ScheduleUpdateRequest;
import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class MySchedule {
    @Id
    @GeneratedValue
    @Column(name = "my_schedule_no")
    private Integer no;
    @Column(name = "root_schedule_no")
    private Integer rootNo;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(columnDefinition = "text")
    private String content;
    @Column(length = 50, nullable = false)
    private String category;
    @Column(nullable = false)
    private Long date;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private RepeatCycle repeatCycle;
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean favorite;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private Alarm alarm;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;

    @OneToMany(mappedBy = "mySchedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<GiftReceive> giftReceives;
    @OneToMany(mappedBy = "mySchedule", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<TributeReceive> tributeReceives;

    // 비즈니스 로직
    public void update(ScheduleUpdateRequest updateRequest) {
        this.name = updateRequest.getName();
        this.content = updateRequest.getContent();
        this.category = updateRequest.getCategory();
        this.date = updateRequest.getDate();
        this.repeatCycle = RepeatCycle.getRepeatCycle(updateRequest.getRepeatCycle());
        this.alarm = Alarm.getAlarm(updateRequest.getAlarm());
    }

    // 비즈니스 로직
    public MySchedule isAllowed(int userId) {
        if (userId != member.getNo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신의 일정만 조회할 수 있습니다.");
        }
        return this;
    }

    public MySchedule duplicateMySchedule(Long nextTime) {
        return MySchedule.builder()
                .name(this.name)
                .rootNo(this.rootNo)
                .content(this.content)
                .category(this.category)
                .date(nextTime)
                .repeatCycle(this.repeatCycle)
                .favorite(this.favorite)
                .alarm(this.alarm)
                .member(this.member)
                .build();
    }

    // 반복 주기에 맞게 엔티티 리스트를 반환하는 메서드
    public static List<MySchedule> generateMySchedules(MySchedule mySchedule) {
        RepeatCycle requestCycle = mySchedule.getRepeatCycle();
        // 사용자가 선택한 날짜
        LocalDateTime originalTime = TimeUtils.UNIX_TO_DATE(mySchedule.getDate());

        List<MySchedule> mySchedules = new LinkedList<>();
        mySchedules.add(mySchedule);

        // 반복 주기가 있으면 사용자가 선택한 날짜 뒤로 일정을 더 추가함
        if (requestCycle.equals(RepeatCycle.WEEK)) {
            for (int i = 1; i < 5 * 12 * 20; i++) {
                LocalDateTime nextTime = originalTime.plusWeeks(i);
                mySchedules.add(mySchedule.duplicateMySchedule(TimeUtils.DATE_TO_UNIX(nextTime)));
            }
        } else if (requestCycle.equals(RepeatCycle.MONTH)) {
            for (int i = 1; i < 12 * 20; i++) {
                LocalDateTime nextTime = originalTime.plusMonths(i);
                if (nextTime.getDayOfMonth() != originalTime.getDayOfMonth()) {
                    continue;
                }
                mySchedules.add(mySchedule.duplicateMySchedule(TimeUtils.DATE_TO_UNIX(nextTime)));
            }
        } else if (requestCycle.equals(RepeatCycle.YEAR)) {
            for (int i = 1; i < 20; i++) {
                LocalDateTime nextTime = originalTime.plusYears(i);
                if (nextTime.getDayOfMonth() != originalTime.getDayOfMonth()) {
                    continue;
                }
                mySchedules.add(mySchedule.duplicateMySchedule(TimeUtils.DATE_TO_UNIX(nextTime)));
            }
        }

        return mySchedules;
    }
}
