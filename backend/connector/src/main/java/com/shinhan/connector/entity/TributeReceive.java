package com.shinhan.connector.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TributeReceive {
    @Id
    @GeneratedValue
    @Column(name = "tribute_receive_no")
    private Integer no;
    private Long amount;
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "my_schedule_no")
    MySchedule mySchedule;
}
