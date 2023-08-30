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
public class SavingsLetter {
    @Id
    @GeneratedValue
    @Column(name = "savings_letter_no")
    private Integer no;
    private String name;
    private String content;
    private Integer currentRound;
    private Integer totalRound;
    private Integer ammount;
    private Long paymentDate;
    private Long startDate;
    private Long endDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;
}
