package com.shinhan.connector.entity;

import com.shinhan.connector.dto.request.ScheduleUpdateRequest;
import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "schedule_no")
    private Integer no;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(columnDefinition = "text")
    private String content;
    @Column(length = 50, nullable = false)
    private String category;
    @Column(nullable = false)
    private Long date;
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private RepeatCycle repeatCycle;
    @Column(nullable = false)
    @ColumnDefault("false")
    private Boolean favorite;
    @Column(length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private Alarm alarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    List<GiftSend> giftSends;
    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY)
    List<TributeSend> tributeSends;

    // 비즈니스 로직
    public void update(ScheduleUpdateRequest updateRequest) {
        this.name = updateRequest.getName();
        this.content = updateRequest.getContent();
        this.category = updateRequest.getCategory();
        this.date = updateRequest.getDate();
        this.repeatCycle = RepeatCycle.getRepeatCycle(updateRequest.getRepeatCycle());
        this.alarm = Alarm.getAlarm(updateRequest.getAlarm());
    }

    public Schedule isAllowed(int userId) {
        if (userId != member.getNo()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신의 일정만 조회할 수 있습니다.");
        }
        return this;
    }
}
