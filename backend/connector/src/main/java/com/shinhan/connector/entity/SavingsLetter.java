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
    @Column(length = 50, nullable = false)
    private String name;
    @Column(columnDefinition = "text")
    private String content;
    @Column(nullable = false)
    private String bankCode;
    @Column(nullable = false)
    private String bankAccount;
    @Column(nullable = false)
    private Integer currentRound;
    @Column(nullable = false)
    private Integer totalRound;
    @Column(nullable = false)
    private Integer amount;
    @Column(nullable = false)
    private Long paymentDate;
    @Column(nullable = false)
    private Long startDate;
    @Column(nullable = false)
    private Long endDate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "friend_no")
    Friend friend;
}
