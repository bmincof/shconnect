package com.shinhan.connector.entity;

import com.shinhan.connector.enums.Alarm;
import com.shinhan.connector.enums.RepeatCycle;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

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
    @Column(length = 100) @NotNull
    private String name;
    @Column(columnDefinition = "text")
    private String content;
    @Column(length = 50) @NotNull
    private String category;
    private Long date;
    @Column(length = 10)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'NONE'")
    private RepeatCycle repeatCycle;
    @NotNull
    @ColumnDefault("false")
    private Boolean favorite;
    @Column(length = 10)
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
}
