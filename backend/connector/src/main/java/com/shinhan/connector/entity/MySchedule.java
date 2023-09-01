package com.shinhan.connector.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MySchedule {
    @Id
    @GeneratedValue
    @Column(name = "my_schedule_no")
    private Integer no;
    private String name;
    @Column(columnDefinition = "text")
    private String content;
    private String category;
    private Long date;
    private Integer repeatCycle;
    private Boolean favorite;
    private Integer alarm;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;

    @OneToMany(mappedBy = "mySchedule", fetch = FetchType.LAZY)
    List<GiftReceive> giftReceives;
    @OneToMany(mappedBy = "mySchedule", fetch = FetchType.LAZY)
    List<TributeReceive> tributeReceives;
}
