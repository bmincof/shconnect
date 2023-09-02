package com.shinhan.connector.entity;

import com.sun.istack.NotNull;
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
    @Column(length = 50) @NotNull
    private String name;
    @Column(columnDefinition = "text")
    private String content;
    @NotNull
    private Integer currentRound;
    @NotNull
    private Integer totalRound;
    @NotNull
    private Integer amount;
    @NotNull
    private Long paymentDate;
    @NotNull
    private Long startDate;
    @NotNull
    private Long endDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;
}
