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
public class Schedule {
    @Id
    @GeneratedValue
    @Column(name = "schedule_no")
    private Integer no;
    private String name;
    private String content;
    private String category;
    private Long date;
    private Integer repeatCycle;
    private Boolean favorite;
    private Integer alarm;

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
