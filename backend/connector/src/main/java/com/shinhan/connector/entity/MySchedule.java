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
public class MySchedule {
    @Id
    @GeneratedValue
    @Column(name = "my_schedule_no")
    private Integer no;
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

    @OneToMany(mappedBy = "mySchedule", fetch = FetchType.LAZY)
    List<GiftReceive> giftReceives;
    @OneToMany(mappedBy = "mySchedule", fetch = FetchType.LAZY)
    List<TributeReceive> tributeReceives;
}
