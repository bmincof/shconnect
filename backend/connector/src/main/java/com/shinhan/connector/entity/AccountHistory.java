package com.shinhan.connector.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountHistory {
    @Id @GeneratedValue
    @Column(name = "account_history_no")
    private Integer accountHistoryNo;
    @Column(name = "depositor_name")
    private String depositorName;
    @Column(name = "bankCode")
    private String bankCode;
    @Column(name = "account_number")
    private String accountNumber;
    @Column(name = "modified_amount")
    private Long modifiedAmount;
    @Column(name = "remain_amount")
    private Long remainAmount;
    private Long date;
    @Column(columnDefinition = "text")
    private String note;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_no")
    private Account account;
}
